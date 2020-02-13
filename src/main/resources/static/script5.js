$(function()
{
    var $rq_form = $('#form_callback');
    var $errorBox = $rq_form.find('.form_errorbox');
    $rq_form.submit(function(){
        if(self.bIsCallbackFormSending)
            return false;
        self.bIsCallbackFormSending = true;
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
                self.bIsCallbackFormSending = false;
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