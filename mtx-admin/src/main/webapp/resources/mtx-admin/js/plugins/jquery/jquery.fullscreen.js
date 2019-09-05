/**
 * @name        jQuery FullScreen Plugin
 * @author      Martin Angelov, Morten Sjøgren
 * @version     1.2
 * @url         http://tutorialzine.com/2012/02/enhance-your-website-fullscreen-api/
 * @license     MIT License
 */

/*jshint browser: true, jquery: true */
(function ($) {
    "use strict";

    // These helper functions available only to our plugin scope.
    function supportFullScreen() {
        var doc = document.documentElement;

        return ('requestFullscreen' in doc) ||
            ('mozRequestFullScreen' in doc && document.mozFullScreenEnabled) ||
            ('webkitRequestFullScreen' in doc) ||
            ('msRequestFullscreen' in doc && document.msFullscreenEnabled);
    }

    function requestFullScreen(elem) {
        if (elem.requestFullscreen) {
            elem.requestFullscreen();
        } else if (elem.mozRequestFullScreen) {
            elem.mozRequestFullScreen();
        } else if (elem.msRequestFullscreen) {
            elem.msRequestFullscreen();
        } else if (elem.webkitRequestFullScreen) {
            elem.webkitRequestFullScreen(Element.ALLOW_KEYBOARD_INPUT);
        }
    }

    function fullScreenStatus() {
        return document.fullscreen ||
            document.mozFullScreen ||
            document.webkitIsFullScreen ||
            //(!!document.getElementById('jquery.fullscreen.div'))||//todo ie fix https://developer.mozilla.org/en-US/docs/Web/API/Fullscreen_API
            false;
    }

    function cancelFullScreen() {
        if (document.exitFullscreen) {
            document.exitFullscreen();
        } else if (document.mozCancelFullScreen) {
            document.mozCancelFullScreen();
        } else if (document.webkitCancelFullScreen) {
            document.webkitCancelFullScreen();
        }else if (document.msExitFullscreen) {
            document.msExitFullscreen();
        }
    }

    function onFullScreenEvent(callback) {
        $(document).on("fullscreenchange mozfullscreenchange webkitfullscreenchange MSFullscreenChange", function (e) {//todo iefix MSFullscreenChange
            // The full screen status is automatically
            // passed to our callback as an argument.
            if(e.type=="MSFullscreenChange"){
                document.fullscreen=!!!document.fullscreen;
                window.setTimeout(function(){callback(fullScreenStatus());},1000)
            }else{
                callback(fullScreenStatus());
            }


        });
    }

    // Adding a new test to the jQuery support object
    $.support.fullscreen = supportFullScreen();

    // Creating the plugin
    $.fn.fullScreen = function (props) {
        if (!$.support.fullscreen || this.length !== 1) {
            // The plugin can be called only
            // on one element at a time

            return this;
        }

        if (fullScreenStatus()) {
            // if we are already in fullscreen, exit
            cancelFullScreen();
            return this;
        }

        // You can potentially pas two arguments a color
        // for the background and a callback function

        var options = $.extend({
                'background': '#111',
                'callback': $.noop(),
                'fullscreenClass': 'fullScreen'
            }, props),

            elem = this,

            // This temporary div is the element that is
            // actually going to be enlarged in full screen

            fs = $('<div>', {
                'id':'jquery.fullscreen.div',
                'css': {//todo 加上css导致ie的高度出问题
                    'overflow-y': 'none',
                    'background': options.background,
                    'width': '100%',
                    'height': '100%'
                }
            })
                .insertBefore(elem)
                .append(elem);

        // You can use the .fullScreen class to
        // apply styling to your element
        elem.addClass(options.fullscreenClass);

        // Inserting our element in the temporary
        // div, after which we zoom it in fullscreen

        requestFullScreen(fs.get(0));

        fs.click(function (e) {
            if (e.target == this) {
                // If the black bar was clicked
                cancelFullScreen();
            }
        });

        elem.cancel = function () {
            cancelFullScreen();
            return elem;
        };

        onFullScreenEvent(function (fullScreen) {
            if (!fullScreen) {
                // We have exited full screen.
                // Detach event listener
                $(document).off('fullscreenchange mozfullscreenchange webkitfullscreenchange MSFullscreenChange');
                // Remove the class and destroy
                // the temporary div

                elem.removeClass(options.fullscreenClass).insertBefore(fs);
                fs.remove();
            }

            // Calling the facultative user supplied callback
            if (options.callback) {
                options.callback(fullScreen);
            }
        });

        return elem;
    };

    $.fn.cancelFullScreen = function () {
        cancelFullScreen();

        return this;
    };
}(jQuery));
