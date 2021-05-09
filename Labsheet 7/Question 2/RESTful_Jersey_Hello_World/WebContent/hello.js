$(document).ready(function() {
    $.ajax({
        xhrFields: {
            withCredentials: true
        },        
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Basic ' + btoa('admin:password123'));
    	},
        url: "http://localhost:8080/RESTful_Jersey_Hello_World/rest/helloworld"
    }).then(function(data, status, jqxhr) {
       $('.greeting-id').append(data.id);
       $('.greeting-content').append(data.content);
       console.log(jqxhr);
    });
});
