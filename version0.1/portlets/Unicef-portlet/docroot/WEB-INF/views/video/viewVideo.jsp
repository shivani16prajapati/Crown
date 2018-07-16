<%@include file="../init.jsp"%>

<p style="text-align:center;" id="videofile">

</p>


<script>
var pattern1 = /(?:http?s?:\/\/)?(?:www\.)?(?:vimeo\.com)\/?(.+)/g;
var pattern2 = /(?:http?s?:\/\/)?(?:www\.)?(?:youtube\.com|youtu\.be)\/(?:watch\?v=)?(.+)/g;
var url = "${viewVideo.videoUrl}";
if(pattern2.test(url)){
	var replacement = '<div class="video-wrapper"><iframe width="420" height="345" src="http://www.youtube.com/embed/$1?rel=0&amp;showinfo=0" frameborder="0" frameborder="0" allow="autoplay; encrypted-media" allowfullscreen></iframe></div>';
	url = url.replace(pattern2, replacement);
	$("#videofile").html(url);
}
if(pattern1.test(url)){
	var replacement = '<div class="video-wrapper"><iframe width="640" height="564" src="https://player.vimeo.com/video/$1" frameborder="0" allowFullScreen mozallowfullscreen webkitAllowFullScreen></iframe></div>';
	url = url.replace(pattern1,replacement);
	$("#videofile").html(url);
}
</script>
<style>
.video-wrapper {
  position: relative;
  padding-bottom: 56.25%; /* 16:9 */
  padding-top: 25px;
  height: 0;
}

.video-wrapper iframe{
  position: absolute;
  top: 0;
  left: 0;
  width: 80%;
  height: 80%;
}
</style>