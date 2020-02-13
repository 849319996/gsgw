if (undefined != self.jQuery)
{
	$(function(){
		if('undefined' != $.carouFredSel)
		{
			$('.world_producers .items').carouFredSel({
				width: 915,
				height: 280,
				align: false,
				auto: false,
				prev: '#prev',
				next: '#next',
				scroll: {
					item: 1,
					duration: 1000,
					easing: 'linear'
				},
				items: {
					visible: 1,
					width: 'variable',
					height: 260
				}
			});
		}
		if('undefined' != $.bxSlider && $('.img-slider .items .slider-item').length >= 3)
		{
			$('.img-slider .items').bxSlider({
				responsive: false,
				pager: false,
				minSlides: 3,
				maxSlides: 3,
				slideWidth: 303, //245
				moveSlides: 1
			});
		}
	});
}