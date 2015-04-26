/**
Video.js is a Javascript objected designed to execute Computer Vision Scripts for Mobile Phones.
Video.js has been designed to add computer vision features on Lego Mindstorms EV3 Robots.
Based Vision API for RCX: http://www.lejos.org/p_technologies/rcx/tutorial/vision/index.html

@author Juan Antonio Breña Moral
*/
var Vision = (function () {

	//CONSTRUCTOR 
	__construct = function() {
		//Empty
	}();

	//PRIVATE PROPERTIES
	var error = false;

	//HTML Structure
	var container = "#camera";
	var content = $('#camera');
	var video = $('#video')[0];
	var canvases = $('canvas');

	var mode;
	var width, height;

	//Image processor
	var processorObj;

	var FPS = 60;

	//PRIVATE METHODS

	/** Method to know if Web Browser has getUserMedia features */
	hasGetUserMedia = function() {
		return !!(navigator.getUserMedia || navigator.webkitGetUserMedia ||
			navigator.mozGetUserMedia || navigator.msGetUserMedia);
	};

	/** Begin to show use Webcam using video Tag */
	startVideo = function(){
	
		if (navigator.getUserMedia) {
		    navigator.getUserMedia(
		        { 'video': true },
		        function(stream)
		        {
		            video.src = stream;
		            video.play();
		        }
		    );
		} else if (navigator.webkitGetUserMedia) {
			//navigator.getUserMedia(constraints, successCallback, errorCallback);
			//Example: https://simpl.info/getusermedia/sources/js/main.js
		    navigator.webkitGetUserMedia
		    (
		        { 'video': true },
		        function(stream)
		        {
		            video.src = window.webkitURL.createObjectURL(stream);
		            video.play();
		        },
		        function (error){
					console.log("navigator.getUserMedia error: ", error);
				}
		    );
		} else if (navigator.mozGetUserMedia) {
		    navigator.mozGetUserMedia
		    (
		        { 'video': true },
		        function(stream)
		        {
		            video.mozSrcObject = stream;
		            video.play();
		        },
		        function(err)
		        {
		            alert('An error occured! '+err);
		        }
		    );
		}
	};

	/** Loop */
	loop = function(){

		processorObj.run();

		window.requestAnimFrame(loop.bind(this));
	}

	/** Method to execute recursively a function in HTML5 */
	window.requestAnimFrame = (function () {
		return window.requestAnimationFrame       ||
			   window.webkitRequestAnimationFrame ||
			   window.mozRequestAnimationFrame    ||
			   window.oRequestAnimationFrame      ||
			   window.msRequestAnimationFrame     ||
			function (callback) {
				window.setTimeout(callback, 1000 / FPS);
			};
	})();

	//PUBLIC PROPERTIES & METHODS
	return {

		/** Event to resive a Canvas object */
		resize: function(){

			var ratio = video.width / video.height;
			var w = $(window).width();
			var h = $(window).height();

			if(mode == "auto"){

				if (content.width() > w) {
					content.width(w);
					content.height(w / ratio);
				} else {
					content.height(h);
					content.width(h * ratio);
				}
			}else{

				content.width(width);
				content.height(height);
			}
	
			canvases.width(content.width());
			canvases.height(content.height());
			content.css('left', (w - content.width()) / 2);
			content.css('top', ((h - content.height()) / 2));

			//Image Processor
			if(typeof processorObj === "undefined"){

			}else{
				processorObj.resize();
			}
		},

		/** Method to define size for a Canvas object */
		setSize: function (){

			if(arguments.length == 1){
				if(arguments[0] == "auto"){
					mode = "auto"
				}
			}else if(arguments.length == 2){
				width = arguments[0];
				height = arguments[1];
			}
		},
	
		/** Method to Init Camera */
		initCamera: function(){
			if ($(container).length ) {
				if(hasGetUserMedia()) {

					if(mode == "auto"){
						this.resize("auto");
					}else{
						this.resize(width,height);
					}

					startVideo();
				}else{
					console.log("getUserMedia() is not supported in your browser. Update your Web Browser.");
					error = true;
				}
			}else{
				console.log("Check HTML used. You are using a bad construction to use Video.js");
				error = true;

			}
		},

		/** Method to define a processor */
		setProcessor: function (obj){

			//Duck Typing
			if (typeof(obj.run) == "function"){
				if (typeof(obj.resize) == "function"){
					processorObj = obj;
				}else{
					console.log("This processor doesn't implement the 'Method' resize");
					error = true;
				}
			}else{
				console.log("This processor doesn't implement the 'Method' run");
				error = true;
			}
			
		},

		/** Method to define FPS */
		setFPS: function (fps){
			FPS = fps;			
		},

		/** Run Video.js */
		run: function(){
			if(!error){
				if(typeof processorObj === "undefined"){
					console.log("Vision.js doesn't have any Image processor");
					error = true;
				}else{
					loop();
				}
			}
		}
	};

})();
