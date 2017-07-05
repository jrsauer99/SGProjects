//home.js
$(document).ready(function() {

loadItems();


	var totalMoney = 0;

	$('#add-dollar-button').on('click',function(){
			totalMoney += 1
			$('#total-money').text(totalMoney.toFixed(2));
			console.log(parseFloat(totalMoney.toFixed(2)));
	});

	$('#add-quarter-button').on('click',function(){
			totalMoney += .25;
			$('#total-money').text(totalMoney.toFixed(2));
			console.log(totalMoney.toFixed(2));
	});

	$('#add-dime-button').on('click',function(){
			totalMoney += .1;
			$('#total-money').text(totalMoney.toFixed(2));
			console.log(totalMoney.toFixed(2));
	});

	$('#add-nickel-button').on('click',function(){
			totalMoney += .05
			$('#total-money').text(totalMoney.toFixed(2));
			console.log(totalMoney.toFixed(2));
	});


	$('.vend-btn').on('click',function(event){			
			var itemNum = parseInt(this.id.charAt(1));			
			$('#item-out').text(itemNum);
	});



	$('#make-purchase-button').on('click',function(event){
			var selectedItem = parseInt($('#item-out').text());
			console.log(selectedItem);
			//console.log("vending!");
			//console.log(this.id);
			//var itemNum = parseInt(this.id.charAt(1));
			//console.log(itemNum);

	//MAKE AJAX CALL!!
			vendItem(selectedItem, totalMoney);

	});
		
});

function loadItems(){
	$.ajax({
        type: 'GET',
        url: 'http://localhost:8080/items',
        success: function(itemArray) { 
        	console.log("success!");
           
            $.each(itemArray, function(index, item){
                var id=item.id;
                var name = item.name;
                var price = item.price;
                var quantity = item.quantity;

                var elName = '#name' + id;
                var elPrice = '#price' + id;
                var elQuantity = '#qty' + id;
                //var elName = '#b' + id + ":nth-child(2)";
                console.log(elName);
                $(elName).text(name);
                $(elPrice).text('$' + price.toFixed(2));
                $(elQuantity).text("Quantity Left: " + quantity);

            });
        },
        error: function() {
            $('#errorMessages')
                .append($('<li>')
                    .attr({class: 'list-group-item list-group-item-danger'})
                    .text('Error calling web service.  Please try again later.'));
        }


    });
}

function vendItem(id, totalMoney){
	
	var itemUrl = 'http://localhost:8080/money/' + totalMoney + '/item/' + id;

	$.ajax({
        type: 'GET',
        url: itemUrl,
        success: function(data, status) { 
        	var quarters = data.quarters;
        	var dimes = data.dimes;
        	var nickels = data.nickels;
        	var pennies = data.pennies;

        	$('#change-out').append('QUARTERS: ' + quarters + '<br>' + 'DIMES: ' + dimes + '<br>' + 'NICKELS: ' + nickels + '<br>' + 'PENNIES: ' + pennies);
        	//$('#change-out').text('DIMES: ' + dimes + '<br>');
        	//$('#change-out').text('NICKELS: ' + nickels + '<br>');
        	//$('#change-out').text('PENNIES: ' + pennies);

        	$('.messages-out p').text("Thank you!");
           
        },
        error: function(data, status, errorThrown) {
            console.log($.parseJSON((data.responseText)).message);
            $('.messages-out p')
                    .attr({class: 'text-danger'})
                    .text($.parseJSON((data.responseText)).message);
        }


    });
}
