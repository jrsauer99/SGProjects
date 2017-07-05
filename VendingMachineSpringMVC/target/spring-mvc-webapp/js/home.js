$(document).ready(function () {
//ADD EVENT LISETNER FOR ITEM BUTTONS
    $('.vend-btn').on('click', function (event) {
        
        if($('#changeAvail').val()==='true'){ //IF CHG IS AVAILABLE
            $('#message-out').text("Please take your change!");
        } else {
            $('#' + this.id).addClass("clicking").delay(250).queue(function () {//DELAY SETS TIMER TO DELAY SUBS ITEMS IN QUEUE
                $(this).removeClass("clicking").dequeue(); //DEQUE LETS JQUERY KNOW WE R DONE WITH THIS
            });
            $('#item-input').val(this.id); //SET VALUE OF ITEM INPUT FIELD WHEN CICKED  
            $('#hiddenItemNum').attr('value', this.id); //UPDATE HIDDEN INPUT FIELD WITH CURRENT SELECTION
            $('#message-out').text("Welcome!");
        }

    });
});

$('.add-money-btn').on('click', function (event) {
    //SET THE HIDDEN INPUT ON THE ADD MONEY FORM TO THE VALUE OF 
    //THIS ITEM INPUT FIELD IN THE PURCHASE FORM, SO THIS VALUE CAN
    //BE PASSED TO SERVER AND BACK
    $('#hiddenItemNum').attr('value', $('#item-input').val());
});

