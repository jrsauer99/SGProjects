//home.js
$(document).ready(function() {

loadItems();
    //INITIALIZE THE VENDING MACHINE
    $('#change-out').hide();  //SET DEFAULT STATE, ONLY SHOWN ON SUCCESSFUL TRANS, OR CHANGE RETURN
    $('#message-out').text("Welcome!");
	var totalMoney = 0;
    
    //*********ADD MONEY BUTTONS***********
    //ADD DOLLARS
	$('#add-dollar-button').on('click',function(){
        if ($('#message-out').text()=="Thank you!"){ //CHECK WHETHER A SUCCESSFUL TRANS. JUST OCCURED
            totalMoney=0; //IF SO RESET MONEY
        }
            totalMoney += 1
			$('#total-money').text(totalMoney.toFixed(2));
            resetChangeBox();
	});

    //ADD QUARTERS 
	$('#add-quarter-button').on('click',function(){
        if ($('#message-out').text()=="Thank you!"){
            totalMoney=0;
        }	
            totalMoney += .25;
			$('#total-money').text(totalMoney.toFixed(2));
            resetChangeBox();
	});

    //ADD DIMES
	$('#add-dime-button').on('click',function(){
        if ($('#message-out').text()=="Thank you!"){
            totalMoney=0;
        }	
            totalMoney += .1;
			$('#total-money').text(totalMoney.toFixed(2));
            resetChangeBox();
	});

    //ADD NICKELS
	$('#add-nickel-button').on('click',function(){
        if ($('#message-out').text()=="Thank you!"){
            totalMoney=0;
        }
            totalMoney += .05
			$('#total-money').text(totalMoney.toFixed(2));
            resetChangeBox();
	});

    //*********SELECT ITEM BUTTONS***********
    //EVENT LISETNER FOR ITEM BUTTONS
	$('.vend-btn').on('click',function(event){			
            //CHANGE CLASS TEMP. TO TRANS SIZE OF BUTTON AND INCLUDE BOX SHADOW
            $('#' + this.id).addClass("clicking").delay(250).queue(function(){//DELAY SETS TIMER TO DELAY SUBS ITEMS IN QUEUE
                $(this).removeClass("clicking").dequeue(); //DEQUE LETS JQUERY KNOW WE R DONE WITH THIS
            });

            //CHECK TO SEE IF A SUCCESSFUL TRANS JUST OCCURRED, IF SO RESET MONEY
            if ($('#message-out').text()=="Thank you!"){
            totalMoney=0;
            }   
            var itemNum = parseInt(this.id.charAt(1));  		
            $('#item-input').val(itemNum); //SET VALUE OF ITEM INPUT FIELD WHEN CICKED 
            resetChangeBox();
	});

    //*********ITEM INPUT FIELD***********
    $('#item-input').on('focus',function(event){                
            if ($('#message-out').text()=="Thank you!"){ //CHECK TO SEE IF A SUCCESSFUL TRANS JUST OCCURRED, 
                totalMoney=0; //IF SO RESET MONEY
            }
            resetChangeBox();
    });
  
    //*********RETURN CHANGE***********
    $('#return-change-button').on('click',function(event){          
            totalMoney = 0;   //RESEST USER MONEY TO 0, AS WE ARE CANCELING PREV TRANSACTION
            changeReturn();
    });

    //*********MAKE PURCHASE***********
	$('#make-purchase-button').on('click',function(event){
			var selectedItem = parseInt($('#item-input').val());
            if ($('#message-out').text()=="Thank you!"){ //CHECK TO SEE IF A SUCCESSFUL TRANS JUST OCCURRED, 
                totalMoney=0; //IF SO RESET MONEY
            }
            if (validateUserInput(selectedItem)){
	           //IF USER INPUT IS VALID MAKE AJAX CALL!!			
                vendItem(selectedItem, totalMoney);
                loadItems();
            }
	});
		
});

//GETS ITEMS FROM WEB SERVICE
function loadItems(){
	$.ajax({
        type: 'GET',
        url: 'http://localhost:8080/items',
        success: function(itemArray) { 
        	console.log("success!");
           
            $.each(itemArray, function(index, item){ //CYCLE THRU EACH AVAILABLE ITEM
                var id=item.id;
                var name = item.name;
                var price = item.price;
                var quantity = item.quantity;

                var elName = '#name' + id;
                var elPrice = '#price' + id;
                var elQuantity = '#qty' + id;
                
                $(elName).text(name); //POPULATE BUTTON TEXT
                $(elPrice).text('$' + price.toFixed(2));
                $(elQuantity).text("Quantity Left: " + quantity);

            });
        },
        error: function() {
            $('#message-out')
                    .attr({class: 'text-danger text-center'})
                    .text('Out of service.  Please try again later.'); //DISPALY IF WEB SERVICE CANNOT BE REACHED
        }
    });
}

//PURCHASE AN ITEM
function vendItem(id, totalMoney){
	
	var itemUrl = 'http://localhost:8080/money/' + totalMoney + '/item/' + id;
    var change;

	$.ajax({
        type: 'GET',
        url: itemUrl,
        success: function(data, status) { 
        	$('#change-out').empty();

            var quarters = data.quarters;
        	var dimes = data.dimes;
        	var nickels = data.nickels;
        	//var pennies = data.pennies;
         
        	$('#change-out').show(); //SHOW THE CHANGE OUT FIELD & APPEND CHANGE INFO
            $('#change-out').append('QUARTERS: ' + quarters + '<br>' + 'DIMES: ' + dimes + '<br>' + 'NICKELS: ' + nickels);// + '<br>' + 'PENNIES: ' + pennies);
        	$('#message-out').removeClass('text-danger'); //BECAUSE SUCCESSFUL, REMOVE RED TEXT STYLE
            $('#message-out').text("Thank you!"); //CHANGE MESSAGE TO THANKYOU, THIS IS HOW THE REST OF PROG WILL KNOW A PURCHASE JUST OCCURED
            totalMoney = 0;//AFTER SUCESSFUL VEND RESET AND CLEAR MONEY FROM SYSTEM
            $('#total-money').text('');

        },
        error: function(data, status, errorThrown) {
            $('#message-out')
                    .attr({class: 'text-danger text-center'})
                    .text($.parseJSON((data.responseText)).message);//GET MESSAGE FROM WEB SERIVCE
        }
    });
}

function changeReturn(){
    loadItems();
    //IF CHANGE OUT IS HIDDEN, THIS MEANS THERE WAS NOT YET A SUCCESSFUL AJAX CALL
    //AND ANY MONEY IN SYSTEM SHOULD BE RETURNED TO THE USED
    if (!($('#change-out').is(':visible'))) {
        //TRANSACTION IS CANCELED, RETURN MONEY TO THE USER
        $('#change-out').empty();
        var returnMoney = parseFloat($('#total-money').text());
        var returnCents = returnMoney * 100;
        var remaining = 0;
        var returnQuarters = 0;
        var returnDimes = 0;
        var returnNickels = 0;
        var returnPennies = 0;
        var moneyInQuarters = 0;
        var moneyInDimes = 0;
        var moneyInNickels = 0;

        returnQuarters = Math.floor(returnCents/25);
        moneyInQuarters = returnQuarters * 25;
        remaining = returnCents - moneyInQuarters;

        returnDimes = Math.floor(remaining/10);
        moneyInDimes = returnDimes * 10;
        remaining -= moneyInDimes;

        returnNickels = Math.floor(remaining/5);
        //moneyInNickels = returnNickels * 5;
        //remaining -= moneyInNickels;

        //returnPennies = remaining;
        $('#change-out').append('QUARTERS: ' + returnQuarters + '<br>' + 'DIMES: ' + returnDimes + '<br>' + 'NICKELS: ' + returnNickels);
        $('#change-out').show(); //SHOW THE CHANGE TO BE RETURNED
    } else {  //THIS MEANS THAT THERE WAS JUST A SUCCESSFUL TRANS, AND WE WANT TO CLEAR OUT
       $('#change-out').empty(); 
    }
    //RESET FIELDS
    $('#message-out').text('');
    $('#total-money').text('');
    $('#item-input').val(''); 
}

//CHECK THE INPUT FIELD FOR RANGE AND PROPER INPUT
function validateUserInput(selectedItem){
    if(selectedItem > 9 || selectedItem < 1 || isNaN(selectedItem)) {
        $('#item-input').val('');
        $('#message-out').text('Choose an Item 1-9');
        return false;
    } else {
        return true;
    }
}

//RESET THE CHANGE FIELD
function resetChangeBox(){
    $('#change-out').empty(); //CLEAR CHANGE MESSAGE WHEN ITEM BUTTON IS CLICKED  
    $('#change-out').hide(); //RESET TO DEFAULT STATE
    $('#message-out').removeClass('text-danger'); //REMOVE RED TEXT
    $('#message-out').text("Welcome!"); //RESET WELCOME MESSAGE

}


