package slogo.model;

import java.util.ArrayList;
import java.util.List;


/**
 * The Class DoublyLinkedList.
 *
 * @param <E> the element type
 */
class DoublyLinkedList<E> {
    
    /** Saved Nodes. */
    private Node myHead;
    private Node myStart;

    /**
     * Gets the Head Node.
     *
     * @return curr
     */
    public E getCurr () {
        if (myHead == null) {
            return null;
        }
        return myHead.contents;
    }

    /**
     * Sets Head to next Node.
     *
     * @return the contents at that Node
     */
    public E redo () {
        if (myHead == null){
            return null;
        }
        
        if (myHead.next == null){
            return myHead.contents;
        }
        
        myHead = myHead.next;
        return myHead.contents;
    }

    /**
     * Sets Head to previous Node.
     *
     * @return the contents at that Node
     */
    public E undo () {
        if (myHead == null || myHead.prev == null){
            return null;
        }
        
        myHead = myHead.prev;
        
        return myHead.contents;
    }

    /**
     * Gets the Previous Node's contents.
     *
     * @return the prev node contents
     */
    public E getPrev () {
        return myHead.prev.contents;
    }

    /**
     * Gets the Next Node's contents.
     *
     * @return the next node contents
     */
    public E getNext () {
        return myHead.next.contents;
    }

    /**
     * Add Node to the end of the list.
     *
     * @param input element
     */
    public void add (E input) {
        Node n = new Node(input);
        if (myHead == null) {
            myHead = n;
            myStart = n;
        }
        else {
            myHead.next = n;
            n.prev = myHead;
            myHead = myHead.next;
        }
    }

    /**
     * Returns list of all Node's contents.
     *
     * @return the list
     */
    public List<E> toList () {
        Node curr = myStart;
        List<E> list = new ArrayList<E>();
        while (curr != null) {
            list.add(curr.contents);
            curr = curr.next;
        }
        return list;
    }

    /**
     * Checks to see if Head has
     * Next node.
     *
     * @return true, if successful
     */
    public boolean hasNext () {
        if (myHead == null) {
            return false;
        }
        return myHead.next != null;
    }

    /**
     * Checks if is list is empty.
     *
     * @return true, if is empty
     */
    public boolean isEmpty () {
        return myHead == null;
    }

    /**
     * Checks to see if Head has
     * a previous Node.
     *
     * @return true, if successful
     */
    public boolean hasPrev () {
        if (myHead == null) {
            return false;
        }
        
        return (myHead.prev != null);
    }

    /**
     * The Inner Class Node.
     */
    private class Node {

        /** The contents. */
        private E contents;
        
        /** The next. */
        private Node next;
        
        /** The prev. */
        private Node prev;

        /**
         * Instantiates a new node.
         *
         * @param input the input
         */
        private Node (E input) {
            contents = input;
        }

        /**
         * Overrides toString to 
         * show the contents of the Node
         * 
         * @return String 
         */
        public String toString () {
            return "" + contents;
        }
    }
}
