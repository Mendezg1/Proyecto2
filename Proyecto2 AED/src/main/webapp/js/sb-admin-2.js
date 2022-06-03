(function($) {
  "use strict"; // Start of use strict

  // Toggle the side navigation
  $("#sidebarToggle, #sidebarToggleTop").on('click', function(e) {
    $("body").toggleClass("sidebar-toggled");
    $(".sidebar").toggleClass("toggled");
    if ($(".sidebar").hasClass("toggled")) {
      $('.sidebar .collapse').collapse('hide');
    };
  });

  // Close any open menu accordions when window is resized below 768px
  $(window).resize(function() {
    if ($(window).width() < 768) {
      $('.sidebar .collapse').collapse('hide');
    };
    
    // Toggle the side navigation when window is resized below 480px
    if ($(window).width() < 480 && !$(".sidebar").hasClass("toggled")) {
      $("body").addClass("sidebar-toggled");
      $(".sidebar").addClass("toggled");
      $('.sidebar .collapse').collapse('hide');
    };
  });

  // Prevent the content wrapper from scrolling when the fixed side navigation hovered over
  $('body.fixed-nav .sidebar').on('mousewheel DOMMouseScroll wheel', function(e) {
    if ($(window).width() > 768) {
      var e0 = e.originalEvent,
        delta = e0.wheelDelta || -e0.detail;
      this.scrollTop += (delta < 0 ? 1 : -1) * 30;
      e.preventDefault();
    }
  });

  // Scroll to top button appear
  $(document).on('scroll', function() {
    var scrollDistance = $(this).scrollTop();
    if (scrollDistance > 100) {
      $('.scroll-to-top').fadeIn();
    } else {
      $('.scroll-to-top').fadeOut();
    }
  });

  // Smooth scrolling using jQuery easing
  $(document).on('click', 'a.scroll-to-top', function(e) {
    var $anchor = $(this);
    $('html, body').stop().animate({
      scrollTop: ($($anchor.attr('href')).offset().top)
    }, 1000, 'easeInOutExpo');
    e.preventDefault();
  });
  
  
  $("#btn-crt-user").click(function(){
				
		$.ajax( {
			
			type: "GET",
			url: '/proy3/Creation?name=' + $('#txt-user').val() ,
			success: function(data) {
			    alert("Resultado: " + data.resultado);
			}
		} );
		
		
	});
  
  
  $("#btn-delete-house").click(function(){ //hola1
				
		$.ajax( {
			
			type: "GET",
			url: '/proy3/Eliminar?name=' + $('#txt-admn-name').val() +'&id=' + $('#txt-id').val() ,
			success: function(data) {
			    alert("Resultado: " + data.resultado);
			}
		} );
		
		
	});

	$("#btn-add-house").click(function(){ //hola1
				
		$.ajax( {
			
			type: "GET",
			url: '/proy3/NewH?name=' + $('#txt-admn-name').val() +'&ide=' + $('#txt-1').val() +'&area=' + $('#txt-2').val() +'&precio=' + $('#txt-3').val() +'&zona=' + $('#txt-4').val() +'&habs=' + $('#txt-5').val() +'&parks=' + $('#txt-6').val() ,
			success: function(data) {
			    alert("Resultado: " + data.resultado);
			}
		} );
		
		
	});

  //Evento del botón que me devuelve el listado de actores
  $("#btn-see-houses").click(function(){
		//alert("The button was clicked 1");
				
		$.ajax( {
			
			type: "GET",
			url: '/proy3/HelloServlet',
			success: function(data) {
				//alert("Result" + data.resultado);
			    var htmlHousesList = '<ul>';
				$.each(data.casas, function(i,item){
					  htmlHousesList += '<li>' + item + '</li>';
				});
				htmlHousesList += '</ul>';
				$('#div-listado-casas').html("");
				$('#div-listado-casas').append(htmlHousesList);
			}
		} );
		
		
	});
	
	$("#btn-search-houses").click(function(){
		//alert("The button was clicked 1");
				
		$.ajax( {
			
			type: "GET",
			url: '/proy3/SearchH?price=' + $('#txt-price').val() + '&area=' + $('#txt-area').val() + '&zone=' + $('#txt-zone').val() + '&habits=' + $('#txt-habits').val() + '&parking=' + $('#txt-parking').val(),
			success: function(data) {
				//alert("Result" + data.resultado);
			    var htmlHousesList = '<ul>';
				$.each(data.encontrados, function(i,item){
					  htmlHousesList += '<li>' + item + '</li>';
				});
				htmlHousesList += '</ul>';
				$('#div-listado-casas').html("");
				$('#div-listado-casas').append(htmlHousesList);
			}
		} );
		
		
	});
	
	 $("#btn-show-likes").click(function(){
		//alert("The button was clicked 1");
				
		$.ajax( {
			
			type: "GET",
			url: '/proy3/SeeSaved?name=' + $('#txt-user').val(),
			success: function(data) {
				//alert("Result" + data.resultado);
			    var htmlHousesList = '<ul>';
				$.each(data.guardados, function(i,item){
					  htmlHousesList += '<li>' + item + '</li>';
				});
				htmlHousesList += '</ul>';
				$('#div-listado-casas').html("");
				$('#div-listado-casas').append(htmlHousesList);
			}
		} );
		
		
	});
	
	//Evento del botón que me devuelve el listado de películas de un determinado actor
	$("#btn-save-house").click(function(){
				
		$.ajax( {
			
			type: "GET",
			url: '/proy3/Guardado?name=' + $('#txt-user').val() + '&id=' + $('#txt-house').val(),
			success: function(data) {
			    alert("Resultado: " + data.resultado);
			}
		} );
		
		
	});

})(jQuery); // End of use strict
