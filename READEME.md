## Known bugs

Although we have support for adding new lines to the linked list, it 
does not display it properly when the display scrolls up or down (when 
you reach the end of the display, or are positioned at the beginning of 
the display). This is because the display tries to insert 1 character at 
a time from the data when it scrolls, causing an 
IndexOutOfBoundsException.

We have implemented support for inserting at the beginning and the end 
of the Linked List representing our data. However, we've focused on 
having a decoupled data and presentation layer, and did not have time to 
update the display in accordance with the data when inserting and 
deleting at the beginning and the end of the list.

There is also a bug regarding the cursorIndex, that makes sure we don't 
end up outside the boundaries of the LinkedList, where you end up not 
being able to delete characters on the screen - instead the cursor just 
moves past the characters. 

Most of these bugs and lacking functionalities are the result of us 
focusing too hard on trying to maintained low coupling and high 
cohesion, instead of focusing on the assignment. We also got stuck on 
tasks that was very fun and challenging, rather than those who are 
related to the datastructure. In furute mandatory assignments we will 
do what the task asks (and make it work), and then move on to 
refactoring and improving the code, before moving on to fun and 
**unnecessary** tasks. 
