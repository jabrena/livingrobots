var DefaultImageProcessor = (function () {

	var video = $("video")[0];
	var canvas = $("canvas")[0];
	var ctx = canvas.getContext("2d");
	var width = video.width;
	var height = video.height;
	var imageData;

	show = function(){

		ctx.drawImage(video, 0, 0, width, height);
		imageData = ctx.getImageData(0, 0, width, height);
		ctx.putImageData(imageData, 0, 0);

	};

	return {

		run: function() {
			show();
		},

		resize: function() {
			width = video.width;
			height = video.height;
		}

	};

})();
