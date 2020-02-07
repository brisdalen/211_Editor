# 211_Editor



## In this assignment we have been instructed to create a text editor that should be capable of inserting, overwriting and deleting text anywhere in the document.


### Key Bindings
* Arrow key up (↑) bound with 'alt-mask modifier' to navigate the cursor up.

* Arrow key down (↓) bound with 'alt-mask modifier' to navigate the cursor down.

* Arrow key right (→) bound with 'alt-mask modifier' to navigate the cursor right.

* Arrow key left (←) bound with 'alt-mask modifier' to navigate the cursor left.

* Backspace (⌫) bound to delete characters.

* Enter (⏎) bound to lineshift.

* Shift modifier (⇧) bound to capital letters.

* Caps Lock (⇪) bound to toggle between captial and lowercase letters.

* Space (␣) bound to insert white space between characters.

* All other keys within the range 1-255 in the ASCII table are bound to their respective symbol (e.g..,)


### Functionality

The text editors allows for inserting characters through the Insert action class that takes an ActionEvent as argument and calls the insertchar() method in document.
The Action Event is parsed from Editors addKeymappings() method that assigns a displayable character from extended ASCII to the equivalent key on the keyboard by iterating through a for loop and assigning each ASCII character to its own KeyEvent.
The insertchar() method handles inserting of characters into the datamodel, and then calls methods for displaying it in the CharacterDisplay window.
To handle deletion we use the DeleteAction class which also takes a ActionEvent argument parsed from addKeymappings(). However, as the deleteAction is only bound to one key on the keyboard we explicitly check for the hexadecimal value of back space (0x008) after iterating over all ASCII characters and assign that action to backspace by casting the hexadecimal value to the primitive type char. 
By explicitly adding 0x008 we assure that typing backspace on the keyboard will only call the DeleteAction class method.
The delete action will call its actionPerformed method that runs the deleteLastchar method from the document class.
The deleteLastChar decreases the column if the cursor's position value is greater than zero.
Then it deletes the current character displayed in the characterDisplay class.



### DataStructure

We chose to use a Linked list, found in java.util, to store the character data.
According to Simon Fraser, storing and manipulating text using an array, are the worst.
Because the entire file must be parsed into the array first, which couple issues with both time and memory.
Furthermore, the deletion or insertion of an extra element would require each element in the array to be moved (Fraiser, 2017).
Implementing a text editor as just one gap buffer is not particularly realistic.
One large edit buffer requires the entire file contents to be stored in a single, contiguous block of memory, which can be difficult to allocate for large files.
Instead, a more realistic strategy is to combine the gap buffer technique with a doubly linked list. 
The benefit of a linked list is that it allows the file to be split across several chunks of memory (Carnegie mellon University).
Which is rather advantageous when inserting characters into a text editor. As LinkedList addlast() method allows for faster ( 0(1))
insertion at the end of the linked list. The same is true for inserting at the beginning. The first node points to the next, whilst the last points the previous.

## Disadvantages
Some disadvantages is traversing and editing in the middle of a LinkedList.
Both add(E element) method and remove (E element) uses O(n) amount of time  to insert in the middle of a list,
given that the index is > 0 and less than list.size(). 
Because it must traverse the linkedlist in order to find the index position.
If the index position is om the last or first element the time per operation is O(1). 

## Improvements
Orginally we thoght To improve our insertion and removal we could add a second in-scope linkedlist that temporaily stores
the characters in new nodes with addlast() and when you stop typing the list appends to the old list in the background. 
In hindsight a ListIterator as cursor would improve inseration and removal .


Piecelist made my microsoft  
 




### Sources

Fraiser, Simon (2017) Text Editor: Data Structures. Retrieved from:
https://www.averylaird.com/programming/the%20text%20editor/2017/09/30/the-piece-table/

Carnegie mellon University (2015) Principles of Imperative Computation, Fall 2015. Retrieved from: 
http://www.cs.cmu.edu/~fp/courses/15122-f15/assignments/editor-writeup.pdf 
