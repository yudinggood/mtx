package com.mtx.demo;

import com.mtx.demo.abstractfactory.AbstractFactory;
import com.mtx.demo.abstractfactory.NorthFactory;
import com.mtx.demo.adapter.CommonAdapter;
import com.mtx.demo.bridge.*;
import com.mtx.demo.build.CommonBuilder;
import com.mtx.demo.build.HighBulider;
import com.mtx.demo.build.House;
import com.mtx.demo.build.HouseDirector;
import com.mtx.demo.chain.CarBodyHandler;
import com.mtx.demo.chain.CarHandler;
import com.mtx.demo.chain.CarHeadHandler;
import com.mtx.demo.chain.CarTailHandler;
import com.mtx.demo.command.*;
import com.mtx.demo.composite.Folder;
import com.mtx.demo.composite.Ifile;
import com.mtx.demo.decorator.*;
import com.mtx.demo.facade.CommonFacade;
import com.mtx.demo.flyweight.MyCharacter;
import com.mtx.demo.flyweight.MyCharacterFactory;
import com.mtx.demo.interpreter.Context;
import com.mtx.demo.interpreter.Expression;
import com.mtx.demo.interpreter.MinusExpression;
import com.mtx.demo.interpreter.PlusExpression;
import com.mtx.demo.iterator.Book;
import com.mtx.demo.iterator.CommonIterator;
import com.mtx.demo.mediator.*;
import com.mtx.demo.memoto.Caretaker;
import com.mtx.demo.methodfactory.AppleFactory;
import com.mtx.demo.methodfactory.BananaFactory;
import com.mtx.demo.observer.CommonObserver;
import com.mtx.demo.observer.Person;
import com.mtx.demo.prototype.Manager;
import com.mtx.demo.prototype.MessageBox;
import com.mtx.demo.prototype.Product;
import com.mtx.demo.prototype.UnderlinePen;
import com.mtx.demo.proxy.RealSubject;
import com.mtx.demo.proxy.SimpleHandle;
import com.mtx.demo.proxy.Subject;
import com.mtx.demo.simplefactory.Fruit;
import com.mtx.demo.simplefactory.FruitFactory;
import com.mtx.demo.singleton.Singleton;
import com.mtx.demo.strategy.MD5;
import com.mtx.demo.strategy.Strategy;
import com.mtx.demo.template.MakeBus;
import com.mtx.demo.template.MakeCar;
import com.mtx.demo.visitor.BusinessReport;
import com.mtx.demo.visitor.CEOVisitor;
import com.mtx.demo.visitor.CTOVisitor;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2018/4/19.
 */
public class Test {
	@org.junit.Test
	public void testSingleton(){
		Singleton s1 =Singleton.getInstance();
		Singleton s2 =Singleton.getInstance();
		s1.setName("111");
		s2.setName("222");
		System.out.println(s1.getName());
		System.out.println(s2.getName());
	}
	@org.junit.Test
	public void testProxy(){//真正的业务功能还是由目标类来实现，代理类只是用于扩展、增强目标类的行为，类似AOP

		/*SimpleProxy simpleProxy=new SimpleProxy();
		simpleProxy.sellBook();*/

		RealSubject realSubject =new RealSubject();
		SimpleHandle simpleHandle=new SimpleHandle();
		simpleHandle.setRealSubject(realSubject);
		Subject subject =(Subject)Proxy.newProxyInstance(RealSubject.class.getClassLoader(),realSubject.getClass().getInterfaces(),simpleHandle);
		subject.sellBook();
	}
	@org.junit.Test
	public void testSimpleFactory() throws InstantiationException, IllegalAccessException, ClassNotFoundException {//简单工厂,一个工厂创建所有对象
		Fruit apple= FruitFactory.getFruit("com.mtx.demo.simplefactory.Apple");
		Fruit banana=FruitFactory.getFruit("com.mtx.demo.simplefactory.Banana");
		apple.getFruit();
		banana.getFruit();
	}
	@org.junit.Test
	public void testMethodFactory() throws IllegalAccessException, InstantiationException, ClassNotFoundException {//工厂方法，每个对象都有一个与之对应的工厂
		com.mtx.demo.methodfactory.FruitFactory applefactory =new AppleFactory();
		com.mtx.demo.methodfactory.Fruit apple=applefactory.getFruit() ;
		apple.getFruit();

		com.mtx.demo.methodfactory.FruitFactory bananafactory =new BananaFactory();
		com.mtx.demo.methodfactory.Fruit banana=bananafactory.getFruit() ;
		banana.getFruit();
	}
	@org.junit.Test
	public void testAbstractFactory(){
		AbstractFactory ff=new NorthFactory();
		com.mtx.demo.abstractfactory.Fruit apple=ff.getApple();
		com.mtx.demo.abstractfactory.Fruit banana=ff.getBanana();
		apple.getFruit();
		banana.getFruit();
	}
	@org.junit.Test
	public void testIterator(){//把若干对象组合到一起，统一执行某操作
		CommonIterator commonIterator=new CommonIterator();
		Book book1=new Book("001","java书",90.0);
		Book book2=new Book("002","php书",91.0);
		commonIterator.addBook(book1);
		commonIterator.addBook(book2);
		Iterator iterator=commonIterator.iterator();
		while (iterator.hasNext()){
			Book book=(Book)iterator.next();
			book.display();
		}

	}
	@org.junit.Test
	public void testObserver(){
		Person person=new Person();
		person.addObserver(new CommonObserver());
		person.setName("1");
	}
	@org.junit.Test
	public void testBuild(){//建造者模式
		CommonBuilder commonBuilder=new HighBulider();
		HouseDirector houseDirector=new HouseDirector();
		houseDirector.makeHouse(commonBuilder);
		House house=commonBuilder.getHouse();
		System.out.println(house.toString());
	}
	@org.junit.Test
	public void testDecorator(){//装饰模式
		Car car=new RunCar();
		CommonDecorator flyDecorator=new FlyDecorator(car);
		CommonDecorator jumpDecorator=new JumpDecorator(flyDecorator);
		jumpDecorator.show();
	}

	@org.junit.Test
	public void testVisitor(){//访问者模式
		BusinessReport report = new BusinessReport();
		System.out.println("=========== CEO看报表 ===========");
		report.showReport(new CEOVisitor());
		System.out.println("=========== CTO看报表 ===========");
		report.showReport(new CTOVisitor());
	}
	@org.junit.Test
	public void testMediator(){//中介者模式
		SmartDevice bd=new BathDevice();
		SmartDevice cd=new CurtainDevice();
		SmartDevice md=new MusicDevice();
		SmartMediator sm=new ConcreteMediator(bd, cd, md);//把设备引用都保存在调停者中
		cd.operateDevice("open",sm); //开启窗帘
		md.operateDevice("close",sm);//关闭音乐

	}
	@org.junit.Test
	public void testChain(){
		CarHandler carHeadHandler=new CarHeadHandler();
		CarHandler carBodyHandler=new CarBodyHandler();
		CarHandler carTailHandler=new CarTailHandler();
		carHeadHandler.setNextHandler(carBodyHandler).setNextHandler(carTailHandler);//组装顺序
		carHeadHandler.handlerCar();
	}
	@org.junit.Test
	public void testBridge(){
		Engine engine2000=new Engine2000();
		Engine engine3000=new Engine3000();
		CarBridge bus=new Bus(engine2000);
		bus.installEngine();
		CarBridge bus2=new Bus(engine3000);
		bus2.installEngine();
	}
	@org.junit.Test
	public void testFlyWeight(){
		MyCharacter myCharacter1=new MyCharacter('a');
		MyCharacter myCharacter2=new MyCharacter('b');
		MyCharacter myCharacter3=new MyCharacter('a');
		myCharacter1.display();
		myCharacter2.display();
		myCharacter3.display();//希望两个a 共享内存
		System.out.println(myCharacter1==myCharacter3);//false

		MyCharacterFactory factory=new MyCharacterFactory();
		MyCharacter my1=factory.getMyCharacter('a');
		MyCharacter my2=factory.getMyCharacter('b');
		MyCharacter my3=factory.getMyCharacter('a');
		my1.display();
		my2.display();
		my3.display();
		System.out.println(my1==my3);//true 实现内存共享
	}

	@org.junit.Test
	public void testFacade(){//外观模式
		CommonFacade commonFacade=new CommonFacade();
		commonFacade.doSomething();

		commonFacade.doSomethingA();
	}
	@org.junit.Test
	public void testMemoto(){//备忘录模式
		com.mtx.demo.memoto.Person person=new com.mtx.demo.memoto.Person("ycg","男");
		Caretaker caretaker=new Caretaker();
		caretaker.setCommonMemoto(person.create());

		System.out.println(person.toString());
		person.setName("ycg1");//修改
		System.out.println(person.toString());
		person.setCommonMemoto(caretaker.getCommonMemoto());//回滚
		System.out.println(person.toString());
	}
	@org.junit.Test
	public void testAdapter(){
		CommonAdapter commonAdapter=new CommonAdapter();
		commonAdapter.use20();

	}
	@org.junit.Test
	public void testPrototype(){//原型模式
		Manager manager = new Manager();
		UnderlinePen underlinePen=new UnderlinePen('~');
		MessageBox mbox=new MessageBox('*');
		MessageBox sbox=new MessageBox('/');
		manager.register("Strong message", underlinePen);
		manager.register("Waring Box", mbox);
		manager.register("Slash Box", sbox);

		Product p1=manager.create("Strong message");
		p1.use("hello world");
		Product p2=manager.create("Waring Box");
		p2.use("hello world");
		Product p3=manager.create("Slash Box");
		p3.use("hello world");

	}
	@org.junit.Test
	public void testInterpreter(){//解释器模式
		List<Expression> list=new ArrayList<>();
		list.add(new PlusExpression());
		list.add(new PlusExpression());
		list.add(new MinusExpression());
		Context context=new Context("10");
		for (Expression expression:list){
			expression.interpreter(context);
			System.out.println(context.getOutput());//11
		}
	}
	@org.junit.Test
	public void testState(){//状态模式
		com.mtx.demo.state.Person person=new com.mtx.demo.state.Person();
		person.setHour(7);
		person.dosomething();
		person.setHour(12);
		person.dosomething();
		person.setHour(7);
		person.dosomething();
	}
	@org.junit.Test
	public void testComposite(){//组合模式
		Folder folder=new Folder("C:");
		Folder folder1=new Folder("yu");
		com.mtx.demo.composite.File file=new com.mtx.demo.composite.File("yu.txt");
		folder.add(folder1);
		folder.add(file);

		displayTree(folder);
	}
	public  static  void displayTree(Ifile root){
		root.display();
		List<Ifile> child =root.getChild();
		for (Ifile ifile:child){
			if(ifile instanceof com.mtx.demo.composite.File){
				ifile.display();
			}else {
				displayTree(ifile);
			}
		}
	}
	@org.junit.Test
	public void testTemplate(){//模板方法
		MakeCar bus=new MakeBus();
		bus.make();

	}

	@org.junit.Test
	public void testStrategy(){//策略模式
		Strategy strategy=new MD5();
		com.mtx.demo.strategy.Context context=new com.mtx.demo.strategy.Context(strategy);
		context.secret();
	}
	@org.junit.Test
	public void testCommand(){//命令模式
		Store store=new Store();
		//store.sellApple();

		CommonCommand appleCommand=new AppleCommand(store);
		CommonCommand bananaCommand=new BananaCommand(store);

		Waiter waiter=new Waiter();
		waiter.setOrder(appleCommand);
		waiter.setOrder(bananaCommand);
		waiter.remove(bananaCommand);
		waiter.sell();
	}
}










