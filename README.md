# 211_Editor

## Authors: Bjørnar Risdalen & Trym Staurheim

## In this assignment we have been instructed to create a text editor that should be capable of inserting, overwriting and deleting text anywhere in the document.



### Key Bindings
* Alt-mask means to simoultanously press alt + key.

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

ScrollDisplayDown() and ScrollDisplayUp() methods in the Document class handles scrolling of the document. ScrollDisplayDown() allows for continous writing by adding a new empty line to the display when you reach the end of the displays size.
ScrollDisplayUp() handles the scrolling upwards in the display with the cursor by getting the previous nodes that was stored in the linkedlist based on row and coloumn of the display. 




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
Both add(E element) method and remove (E element) uses O(n) time complexity to insert in the middle of a list,
given that the index is > 0 and less than list.size(). 
Because it must traverse the linkedlist in order to find the index position.
If the index position is om the last or first element the time per operation is O(1). 

## Improvements
Orginally we thought that to improve our insertion and removal we could add a second in-scope linkedlist that temporaily stores
the characters in new nodes with addlast() and when you stop typing the list appends to the old list in the background. 
In hindsight a ListIterator as cursor would improve inseration and removal because it would know the position of linkedlist and could then use ListIterator.add() and remove() methods to insert/remove with O(1) per operation.

## PieceTable
The problem of choosing a datastructure for displaying text in an editor has long since been solved. Piece table was used in  microsoft word all the way back in 1984 and VS studio code converted from an array of lines to a piece table(Burns, 2019: Lyu, 2018). The main benefit of piece table is that the table contains the whole file contents in the original field. The original field of text is stored in a single node. When a user types in new text it is allways added at the end of the original node through appending the new content to the added field, followed by insertion of a new node of type added. When a user makes edits in the middle of a node, that node is split and a new is inserted(Lyu, 2018).

For example, to access the second line in a node instance, you can read the nodes.linestarts position 0 (node.lineStarts[0]) and its second position 1 (node.lineStarts[1]) which will give the relative offsets for which a line starts and ends. Due to knowing how many line breaks are present in a given node, accessing a random line in the document is straight forward: (e.g.., O(1) time complexity)by reading each node starting from the first one until we find the targeted line break (Lyu, 2018).
The Piece table data structure is not present in the standard java class library but could be implemented in your own projects, and could serve as the better data structure to display text in a Editor. 

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
*unnecessary* tasks. 


### Sources

Fraiser, Simon (2017) Text Editor: Data Structures. Retrieved from:
https://www.averylaird.com/programming/the%20text%20editor/2017/09/30/the-piece-table/

Carnegie mellon University (2015) Principles of Imperative Computation, Fall 2015. Retrieved from: 
http://www.cs.cmu.edu/~fp/courses/15122-f15/assignments/editor-writeup.pdf

When to use LinkedList over ArrayList in Java?. Retrieved from:
https://stackoverflow.com/questions/322715/when-to-use-linkedlist-over-arraylist-in-java

Burns, Darren (2019)The Piece Table - the Unsung Hero of Your Text Editor. Retrieved from:
https://darrenburns.net/posts/piece-table/

Lyu, Peng (2018) Text Buffer Reimplementation. Retrieved from:
https://code.visualstudio.com/blogs/2018/03/23/text-buffer-reimplementation
