/**
 * 
 */

function getDetails() {
	jQuery
			.ajax({
				url : "http://localhost:8080/book_service/rest/books/"
						+ $("#id").val(),
				type : "GET",
				contentType : "application/json",
				dataType : 'json',
				success : function(data, textStatus, errorThrown) {
					// here is your json.
					// process it
					$("#title").text(data.title);
					$("#price").text(data.price);

				},
				error : function(jqXHR, textStatus, errorThrown) {
					$("#title").text("Sorry! Book not found!");
					$("#price").text("");
				},
				timeout : 120000,
			});
};

function deleteBook() {
	
	var id = $("#DeleteBookId").val();

	$.ajax({
		
		url : "http://localhost:8080/book_service/rest/books/" + id,
		
		type : "DELETE",
		
		contentType : "application/json",
		
		success : function() {
			
			$("#result2").text("Book Deleted.");
			
		},
		
		error : function() {
			
			$("#result2").text("Delete Failed.");
			
		}
	});

}

function addBook() {

	var id = $("#BookId").val();
	var title = $("#BookName").val();
	var price = $("#BookPrice").val();

	var array = {
		id : id,
		title : title,
		price : price
	}

	$.ajax({

		type : "POST",

		url : "http://localhost:8080/book_service/rest/books",

		contentType : "application/json",

		dataType : 'json',

		data : JSON.stringify(array),

		success : function(data) {
			$("#result1").text("Book Added Successfully.");
		},

		error : function(data) {
			$("#result1").text("Book Adding Failed.");
		}

	});

}
