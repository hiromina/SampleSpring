
$(function(){
  $('#frmMain').submit(function() {
	var jsonData = {
    title: $('input[name="title"]').val(),
    summary: $('input[name="summary"]').val(),
    status:$('select[name="status"]').val()
  };
  $("#demo").jsForm({
    data:jsonData
  });
    alert(JSON.stringify($("#demo").jsForm("get"), null, " "));
    var text = JSON.stringify($("#demo").jsForm("get"), null, " ");

    $.ajax({
    'url':           this.action,
    'type':          'post',
    'dataType':      'json',
    'contentType':   'application/json',
    'data':          text
  })

  .done(function(data, textStatus, xhr){
  })
  .fail(function(xhr, textStatus, errorThrown){
    var text = JSON.stringify(
      {
        "XMLHttpRequest": xhr,
        "textStatus": textStatus,
        "errorThrown": errorThrown
      },
      undefined, 2
    );
    //alert(text);
  });

  return false;

  });
});

