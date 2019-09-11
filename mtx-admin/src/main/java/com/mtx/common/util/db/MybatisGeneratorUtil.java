package com.mtx.common.util.db;

import com.mtx.common.util.base.FileUtil;
import com.mtx.common.util.base.StringUtil;
import com.mtx.common.util.secret.AESUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ObjectUtils;
import org.apache.velocity.VelocityContext;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 代码生成类
 */
@Slf4j
public class MybatisGeneratorUtil {
    // generatorConfig模板路径
    private static String GENERATORCONFIG_VM = "/template/generatorConfig.vm";
    // Service模板路径
    private static String SERVICE_VM = "/template/Service.vm";
    // ServiceMock模板路径
    private static String SERVICEMOCK_VM = "/template/ServiceMock.vm";
    // ServiceImpl模板路径
    private static String SERVICEIMPL_VM = "/template/ServiceImpl.vm";
    // ServiceImpl模板路径
    private static String CONTROLLER_VM = "/template/Controller.vm";


    /**
     * 根据模板生成generatorConfig.xml文件
     * @param jdbcDriver   驱动路径
     * @param jdbcUrl      链接
     * @param jdbcUsername 帐号
     * @param jdbcPassword 密码
     * @param module        项目模块
     * @param database      数据库
     * @param tablePrefix  表前缀
     * @param packageName  包名
     */
    public static void generator(
            String jdbcDriver,
            String jdbcUrl,
            String jdbcUsername,
            String jdbcPassword,
            String module,
            String database,
            String tablePrefix,
            String packageName,
            Map<String, String> lastInsertIdTables) throws Exception{

        String os = System.getProperty("os.name");
        String targetProject = "mtx-admin";
        String basePath = MybatisGeneratorUtil.class.getResource("/").getPath().replace("/target/classes/", "").replace(targetProject, "");
        if (os.toLowerCase().startsWith("win")) {
            GENERATORCONFIG_VM = MybatisGeneratorUtil.class.getResource(GENERATORCONFIG_VM).getPath().replaceFirst("/", "");
            SERVICE_VM = MybatisGeneratorUtil.class.getResource(SERVICE_VM).getPath().replaceFirst("/", "");
            SERVICEMOCK_VM = MybatisGeneratorUtil.class.getResource(SERVICEMOCK_VM).getPath().replaceFirst("/", "");
            SERVICEIMPL_VM = MybatisGeneratorUtil.class.getResource(SERVICEIMPL_VM).getPath().replaceFirst("/", "");
            basePath = basePath.replaceFirst("/", "");
        } else {
            GENERATORCONFIG_VM = MybatisGeneratorUtil.class.getResource(GENERATORCONFIG_VM).getPath();
            SERVICE_VM = MybatisGeneratorUtil.class.getResource(SERVICE_VM).getPath();
            SERVICEMOCK_VM = MybatisGeneratorUtil.class.getResource(SERVICEMOCK_VM).getPath();
            SERVICEIMPL_VM = MybatisGeneratorUtil.class.getResource(SERVICEIMPL_VM).getPath();
        }

        String generatorConfigXml = MybatisGeneratorUtil.class.getResource("/").getPath().replace("/target/classes/", "") + "/src/main/resources/generatorConfig.xml";
        targetProject = basePath + targetProject;
        //String sql = "SELECT table_name FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = '" + database + "' AND table_name LIKE '" + tablePrefix + "_%';";
        String sql = "SELECT table_name FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = ? AND table_name LIKE ? ;";
        List list=new ArrayList();
        list.add(database); list.add(tablePrefix+"_%");

        log.info("========== 开始生成generatorConfig.xml文件 ==========");
        List<Map<String, Object>> tables = new ArrayList<>();
        try {
            VelocityContext context = new VelocityContext();
            Map<String, Object> table;

            // 查询定制前缀项目的所有表
            JdbcUtil jdbcUtil = new JdbcUtil(jdbcDriver, jdbcUrl, jdbcUsername, AESUtil.aesDecode(jdbcPassword));
            List<Map> result = jdbcUtil.selectByParams(sql, list);
            for (Map map : result) {
                String tableName =map.get("TABLE_NAME").toString();
                log.info(tableName);
                tableName =tableName.replaceAll("\\$","_");

                table = new HashMap<>(2);
                table.put("table_name", tableName);
                table.put("model_name", StringUtil.lineToHump(ObjectUtils.toString(tableName)));
                tables.add(table);
            }
            jdbcUtil.release();

            String targetProjectSqlMap = basePath + "mtx-admin";
            context.put("tables", tables);
            context.put("generator_javaModelGenerator_targetPackage", packageName + ".dao.model");
            context.put("generator_sqlMapGenerator_targetPackage", packageName + ".dao.mapper");
            context.put("generator_javaClientGenerator_targetPackage", packageName + ".dao.mapper");
            context.put("targetProject", targetProject);
            context.put("targetProject_sqlMap", targetProjectSqlMap);
            context.put("generator_jdbc_password", AESUtil.aesDecode(jdbcPassword));
            context.put("last_insert_id_tables", lastInsertIdTables);
            VelocityUtil.generate(GENERATORCONFIG_VM, generatorConfigXml, context);
            // 删除旧代码
            FileUtil.deleteDir(new File(targetProject + "/src/main/java/" + packageName.replaceAll("\\.", "/") + "/dao/model"));
            FileUtil.deleteDir(new File(targetProject + "/src/main/java/" + packageName.replaceAll("\\.", "/") + "/dao/mapper"));
            FileUtil.deleteDir(new File(targetProjectSqlMap + "/src/main/java/" + packageName.replaceAll("\\.", "/") + "/dao/mapper"));
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        log.info("========== 结束生成generatorConfig.xml文件 ==========");

        log.info("========== 开始运行MybatisGenerator ==========");
        List<String> warnings = new ArrayList<>();
        File configFile = new File(generatorConfigXml);
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
        for (String warning : warnings) {
            log.info(warning);
        }
        log.info("========== 结束运行MybatisGenerator ==========");

        log.info("========== 开始生成Service ==========");
        String ctime = new SimpleDateFormat("yyyy/M/d").format(new Date());
        String servicePath = basePath + "mtx-admin" + "/src/main/java/" + packageName.replaceAll("\\.", "/") + "/rpc/api";
        String serviceImplPath = basePath + "mtx-admin" + "/src/main/java/" + packageName.replaceAll("\\.", "/") + "/rpc/service/impl";
        for (int i = 0; i < tables.size(); i++) {
            String model = StringUtil.lineToHump(ObjectUtils.toString(tables.get(i).get("table_name")));
            String service = servicePath + "/" + model + "Service.java";
            String serviceMock = servicePath + "/" + model + "ServiceMock.java";
            String serviceImpl = serviceImplPath + "/" + model + "ServiceImpl.java";
            // 生成service
            File serviceFile = new File(service);
            if (!serviceFile.exists()) {
                VelocityContext context = new VelocityContext();
                context.put("package_name", packageName);
                context.put("my_model", model);
                context.put("ctime", ctime);
                VelocityUtil.generate(SERVICE_VM, service, context);
                log.info(service);
            }
            // 生成serviceMock
            File serviceMockFile = new File(serviceMock);
            if (!serviceMockFile.exists()) {
                VelocityContext context = new VelocityContext();
                context.put("package_name", packageName);
                context.put("my_model", model);
                context.put("ctime", ctime);
                VelocityUtil.generate(SERVICEMOCK_VM, serviceMock, context);
                log.info(serviceMock);
            }
            // 生成serviceImpl
            File serviceImplFile = new File(serviceImpl);
            if (!serviceImplFile.exists()) {
                VelocityContext context = new VelocityContext();
                context.put("package_name", packageName);
                context.put("my_model", model);
                context.put("mapper", StringUtil.toLowerCaseFirstOne(model));
                context.put("ctime", ctime);
                VelocityUtil.generate(SERVICEIMPL_VM, serviceImpl, context);
                log.info(serviceImpl);
            }
        }
        log.info("========== 结束生成Service ==========");
    }
}
