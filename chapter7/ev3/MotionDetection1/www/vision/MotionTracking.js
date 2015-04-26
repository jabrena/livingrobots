/**
Motion tracking is a Image Processor for Video.js
*/
var MotionTracking = (function () {

	var video = $("video")[0];
	var canvas = $("canvas")[0];
	var ctx = canvas.getContext("2d");
	var width = video.width;
	var height = video.height;
	var imageData;

	var tracked = false;

	var targetRed = 14;
	var targetGreen = 145;
	var targetBlue = 84;

	var left,right, top2, bottom = -1;

	var sensitivity = 50;

	relMouseCoords = function (e, elem){
		var mouseX, mouseY;

		if(e.offsetX) {
		    mouseX = e.offsetX;
		    mouseY = e.offsetY;
		}
		else if(e.layerX) {
		    mouseX = e.layerX;
		    mouseY = e.layerY;
		}

		return {x:mouseX, y:mouseY}
	};

	componentToHex = function (c) {
	    var hex = c.toString(16);
	    return hex.length == 1 ? "0" + hex : hex;
	};

	rgbToHex = function (r, g, b) {
	    return "#" + componentToHex(r) + componentToHex(g) + componentToHex(b);
	};

	hexToRgb =function (hex) {
	    var result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
	    return result ? {
		r: parseInt(result[1], 16),
		g: parseInt(result[2], 16),
		b: parseInt(result[3], 16)
	    } : null;
	};

	drawObject = function(ctx){

		var scale = 0;

		var trackedColor = rgbToHex(targetRed,targetGreen,targetBlue);			

		ctx.strokeStyle = trackedColor;
		ctx.fillStyle = trackedColor;
		ctx.lineWidth = 2;
		ctx.beginPath();

		var vH = video.videoHeight, 
		vW = video.videoWidth;

		var pixelPerScaleValue = scale*10;

		x  = left*2 - pixelPerScaleValue;
		x2 = right*2 + pixelPerScaleValue;
		y = top2*2 - pixelPerScaleValue;
		y2 = bottom*2 + pixelPerScaleValue;

		var width = x2-x;
		var height = y2-y;    

		ctx.rect(x/2, y/2, width/2, height/2);
		//ctx.fillRect(x/2, y/2, width/2, height/2);
		ctx.stroke();
	};

	detectObject = function(imageData,width){
		for(var i = 0, l = imageData.data.length; i < l;i+=4) {
		    var red = imageData.data[i];
		    var green = imageData.data[i + 1];
		    var blue = imageData.data[i + 2];

		    var x = (i / 4) % width;
		    var y = (i / 4) / width;

		    var diff = Math.sqrt(Math.pow(targetRed - red, 2)
			+ Math.pow(targetGreen - green, 2)
			+ Math.pow(targetBlue - blue, 2));

		    if (diff < sensitivity) {

			left = left == -1 ? x : Math.min(left, x);
			right = right == -1 ? x : Math.max(right, x);

			top2 = top2 == -1 ? y : Math.min(top2, y);
			bottom = bottom == -1 ? y : Math.max(bottom, y);
		    }

		}
	};

	return {

		train: function(strColor){

			if(!this.error){

				if('undefined' != typeof strColor){
					var rgb = hexToRgb(strColor);
					targetRed = rgb.r;
					targetGreen = rgb.g;
					targetBlue = rgb.b;
					console.log("Color tracked: " + strColor);

					tracked = true;
				}else{
					canvas.addEventListener("click", function(evt) {
						var pos = relMouseCoords(evt, canvas);

						ctx.drawImage(video, 0, 0, width, height);

						var pixel = ctx.getImageData(parseInt(pos.x), parseInt(pos.y), 1, 1).data;
						targetRed = pixel[0];
						targetGreen = pixel[1];
						targetBlue = pixel[2];

						console.log("Color tracked: " + rgbToHex(targetRed,targetGreen,targetBlue));

						evt.preventDefault();
						tracked = true;

						return false;
					});
				}
			}
		},

		track: function(flag){
			tracked = flag;
		},

		isTracked: function(){
			return tracked;
		},

		setSensitivity: function(value){
			sensitivity = value;
		},

		// A public function utilizing privates
		run: function() {

			ctx.drawImage(video, 0, 0, width, height);
			imageData = ctx.getImageData(0, 0, width, height);
			ctx.putImageData(imageData, 0, 0);

			left=right=top2=bottom = -1;

			if(tracked){

				detectObject(imageData,width);

				if(left != -1) {
					drawObject(ctx);
				}
			}
		},

		resize: function() {
			width = video.width;
			height = video.height;
		}
	};

})();

//TODO: Improve Javascript OOP
//http://stackoverflow.com/questions/1114024/constructors-in-javascript-objects
