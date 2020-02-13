$(function()
{
    var $rq_form = $('#form_news');
    var $errorBox = $rq_form.find('.form_errorbox');
    $rq_form.submit(function(){
        if(self.bIsNewsFormSending)
            return false;
        self.bIsNewsFormSending = true;
        $('#ajax_loader').show();
        var $form = $(this).serializeArray();
        $errorBox.html('');
        $form.push({ name: 'ajax', value: true });
        $.post(
            $(this).attr('action'),
            $form,
            function(answ)
            {
                answ = answ || {};
                self.bIsNewsFormSending = false;
                $('#ajax_loader').hide();
                switch (answ.status)
                {
                    case 'ok':
                        $errorBox.html('');
                        $rq_form.get(0).reset();
                        $('.overlay, .done').fadeIn();
                        popup_position();
                        break;
                    default: // error
                        $errorBox.html(answ.msg);
                }
            },
            'json'
        );
        return false;
    });
});