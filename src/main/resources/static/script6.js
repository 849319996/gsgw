if (undefined != jQuery)
{
    $(function() {
        $('<div/>', {id: 'ajax_loader'})
            .appendTo('body')
            .hide();

        $(document)
            .ajaxError(function(event, jqXHR, ajaxSettings, thrownError){
                if (thrownError.toString().length)
                    alert('При обращении к странице ' + ajaxSettings.url + ' возникла ошибка: ' + thrownError);
            })
            .ajaxStart(function(){
                clearTimeout(self.ajaxTimer);
                self.ajaxTimer = setTimeout(
                    function(){
                        $('#ajax_loader').show();
                    },
                    100 // timeout, чтобы не мелькало на быстрых запросах
                );
            })
            .ajaxStop(function(){
                clearTimeout(self.ajaxTimer);
                $('#ajax_loader').hide();
            });
    });
}