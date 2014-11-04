var MoustacheProcessor = (function () {

	var video = $("video")[0];
	var canvas = $("canvas")[0];
	var ctx = canvas.getContext("2d");
	var width = video.width;
	var height = video.height;
	var imageData;
	var img;

	__construct = function() {
		img = new Image();
		img.src = mustache;
	}();

	show = function(){

		ctx.drawImage(video, 0, 0, width, height);
		imageData = ctx.getImageData(0, 0, width, height);
		ctx.putImageData(imageData, 0, 0);

		var s = (new Date()).getTime();

		//https://github.com/wesbos/HTML5-Face-Detection/tree/master/scripts
		var d = ccv.detect_objects({
			    canvas: ccv.pre(canvas),
			    cascade: cascade,
			    interval: 5,
			    min_neighbors: 1
			});

		console.log("Elapsed time : " + ((new Date()).getTime() - s).toString() + "ms");

		ctx.lineWidth = 3;
		ctx.strokeStyle = "#f00";
		for (var i = 0; i < d.length; i++) {
			//ctx.strokeRect(d[i].x, d[i].y, d[i].width, d[i].height);
			//ctx.drawImage(img,10,10);
			//ctx.drawImage(img,d[i].x, d[i].y, d[i].width, d[i].height);
			ctx.drawImage(img,d[i].x, d[i].y + d[i].height*0.70, d[i].width, d[i].height/2);
			//ctx.drawImage(img, 0, 0, width, height, d[i].x + d[i].width / 2 - width/2, d[i].y +  d[i].height*0.75, width, height);
		}

/*
			if(d.length > 0){
				ctx.strokeStyle = "#ff5714";
				ctx.fillStyle = "#ff5714";
				ctx.lineWidth = 2;
				ctx.beginPath();

				for (var c = 0, b; b=d[c++];){
					var factor = b.width/100;
					var width = 73*factor;
					var height = 20*factor;
					//ctx.drawImage(img, 0, 0, width, height, b.x + b.width / 2 - width/2, b.y +  b.height*0.75, width, height);
					//ctx.strokeStyle = 'red';
					ctx.rect(b.x, b.y, b.width, b.height);
					ctx.stroke();
				}
			}
*/
	/*
			for (var c = 0, b; b=d[c++];){
				var factor = b.width/100;
				var width = 73*factor;
				var height = 20*factor;
				ctx.drawImage(img, 0, 0, width, height, b.x + b.width / 2 - width/2, b.y +  b.height*0.75, width, height);
				ctx.strokeStyle = 'red';
			}
	*/
			/*
			//var d = ccv.detect_objects({canvas:ccv.grayscale(ccv.pre(img2)),cascade:cascade,interval:5,min_neighbors:1});
			var img = new Image;
			img.src = mustache;
			img.onload = function(){

			};
			*/


	};

	return {

		init: function(){
			img = new Image();
			img.src = mustache;
		},

		run: function() {
			show();
		},

		resize: function() {
			width = video.width;
			height = video.height;
		}

	};

})();
