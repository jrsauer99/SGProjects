//home.js
$(document).ready(function() {

    loadItems();
    //INITIALIZE THE VENDING MACHINE
    $('#change-out').hide();  //SET DEFAULT STATE, ONLY SHOWN ON SUCCESSFUL TRANS, OR CHANGE RETURN
    $('#message-out').text("Welcome!");
	var totalMoney;
    $('#total-money').text(0);
     	
});

//GETS ITEMS FROM WEB SERVICE
function loadItems(){
	$.ajax({
        type: 'GET',
        url: 'http://localhost:8080/items',
        success: function(itemArray) { 
        	console.log("success!");

            $('#button-column').empty();
           
            $.each(itemArray, function(index, item){ //CYCLE THRU EACH AVAILABLE ITEM
                var id=item.id;
                var name = item.name;
                var price = '$' + (item.price).toFixed(2);
                var quantity = item.quantity;
                var buttonContent = '';
              
                    buttonContent +=  '<div class="col-md-4">';
                         buttonContent += '<button type="button" ';
                            buttonContent += 'id="b' + id + '"';
                            buttonContent += ' class="btn btn-default btn-block vend-btn">';  
                            buttonContent += '<h4 class="text-left" id="item-num">' + id + '</h4>';
                            buttonContent += '<h3 class="text-center" id="name' + id + '">'+ name + '</h3>';
                            buttonContent += '<h4 id="price' + id + '">' + price + '</h4>';
                            buttonContent += '<br>';
                            buttonContent += '<br>';
                            buttonContent += '<h4 id="qty' + id + '">Quanity Left: ' + quantity + '</h4>';
                        buttonContent += '</button>'
                    buttonContent += '</div>'

                $('#button-column').append(buttonContent);

            });

            //ADD EVENT LISETNER FOR ITEM BUTTONS
             $('.vend-btn').on('click',function(event){
            console.log('you clicked me');          
            //CHANGE CLASS TEMP. TO TRANS SIZE OF BUTTON AND INCLUDE BOX SHADOW
            $('#' + this.id).addClass("clicking").delay(250).queue(function(){//DELAY SETS TIMER TO DELAY SUBS ITEMS IN QUEUE
                $(this).removeClass("clicking").dequeue(); //DEQUE LETS JQUERY KNOW WE R DONE WITH THIS
            });

            var itemNum = parseInt(this.id.charAt(1));  
            console.log(itemNum);       
            $('#item-input').val(itemNum); //SET VALUE OF ITEM INPUT FIELD WHEN CICKED 
            resetChangeBox();
    });

        },
        error: function() {
            $('#message-out')
                    .attr({class: 'text-danger text-center'})
                    .text('Out of service.  Please try again later.'); //DISPALY IF WEB SERVICE CANNOT BE REACHED
        }
    });
}

//*********ADD MONEY BUTTONS***********
$('.add-money-btn').on('click',function(){
    totalMoney=parseFloat($('#total-money').text());
    totalMoney += parseFloat(($(this).attr("value")));
    $('#total-money').text(totalMoney.toFixed(2));       
    resetChangeBox();
});


//*********ITEM INPUT FIELD***********
$('#item-input').on('focus',function(event){                
    resetChangeBox();
});
  
//*********RETURN CHANGE***********
$('#return-change-button').on('click',function(event){          
        changeReturn();
        $('#total-money').text(0);  //RESEST USER MONEY TO 0, AS WE ARE CANCELING PREV TRANSACTION
});

//*********MAKE PURCHASE***********
$('#make-purchase-button').on('click',function(event){
    var selectedItem = parseInt($('#item-input').val());
    totalMoney = totalMoney=parseFloat($('#total-money').text());
        if (validateUserInput(selectedItem)){
            //IF USER INPUT IS VALID MAKE AJAX CALL!!            
            vendItem(selectedItem, totalMoney);
            loadItems();
        }
});


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
        	
            returnChange(quarters, dimes, nickels);
        	$('#message-out').removeClass('text-danger'); //BECAUSE SUCCESSFUL, REMOVE RED TEXT STYLE
            $('#message-out').text("Thank you!"); //CHANGE MESSAGE TO THANKYOU, THIS IS HOW THE REST OF PROG WILL KNOW A PURCHASE JUST OCCURED
            totalMoney = 0;//AFTER SUCESSFUL VEND RESET AND CLEAR MONEY FROM SYSTEM
            $('#total-money').text(0);

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
        
        //NOT SHOWING!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!???????????????????
        returnChange(returnQuarters, returnDimes, returnNickels);
        
    } else {  //THIS MEANS THAT THERE WAS JUST A SUCCESSFUL TRANS, AND WE WANT TO CLEAR OUT
       $('#change-out').empty(); 
    }
    //RESET FIELDS
    $('#message-out').text('');
    $('#total-money').text(0);
    $('#item-input').val(''); 
}

//CHECK THE INPUT FIELD FOR RANGE AND PROPER INPUT
function validateUserInput(selectedItem){
    numButtons = $('.vend-btn').size();
    if(selectedItem > numButtons || selectedItem < 1 || isNaN(selectedItem)) {
        $('#item-input').val('');
        $('#message-out').attr({class: 'text-danger text-center'});
        $('#message-out').text('Choose an Item 1-' + numButtons);
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

function returnChange(quarters, dimes, nickels) {

    if (quarters > 0){
        $('#change-out').append('QUARTERS: ' + quarters + '<br>');
    }
    if (dimes > 0) {
        $('#change-out').append('DIMES: ' + dimes + '<br>');
    }
    if (nickels > 0) {
        $('#change-out').append('NICKELS: ' + nickels + '<br>');
    }
    $('#change-out').show(); //SHOW THE CHANGE TO BE RETURNED
}


