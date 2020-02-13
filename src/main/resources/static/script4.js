$(function()
{
    self.strTransportName = self.strTransportName || 'ajax_file_transport';
    if(!$('body iframe[name="'+self.strTransportName+'"]').length)
    {
        $('<iframe/>', {
            src: 'about:blank',
            name: self.strTransportName,
            width: 1,
            height: 1
        }).appendTo('body').hide();
    }
    $('#form_resume')
        .attr('target', self.strTransportName)
        .append('<input type="hidden" name="ajax" value="true">')
        .submit(function(){
            if (self.bIsResumeFormSending)
                return false;
            self.bIsResumeFormSending = true;
            $('#ajax_loader').show();
            $(this).find('.form_errorbox').html('');
            return true;
        });
});

function __ajaxFileTransportCallbackResume(answ)
{
    answ = answ || {};
    self.bIsResumeFormSending = false;
    $('#ajax_loader').hide();
    var $rq_form = $('#form_resume');
    var $errorBox = $rq_form.find('.form_errorbox');
    switch (answ.status)
    {
        case 'ok':
            $errorBox.html('');
            $rq_form.get(0).reset();
            $('http://gcmatrix.com/local/components/matrix/form.request_resume/templates/.default/.overlay, .done').fadeIn();
            popup_position();
            break;
        default: // error
            $errorBox.html(answ.msg);
    }
}