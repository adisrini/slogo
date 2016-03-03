package slogo.model;

import java.util.ArrayList;
import java.util.List;


class DoublyLinkedList<E> {
    private Node myHead;
    private Node myStart;

    public E getCurr () {
        if (myHead == null) {
            return null;
        }
        return myHead.contents;
    }

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
     * Sets head to previous Node
     * 
     * @return the contents at that Node
     */
    public E undo () {
        // TODO: THROW EXCEPTION IF THERE IS NO STATE PREVIOUS
        // System.out.println("my head "+myHead);
        // System.out.println(myHead.prev);
        //
        // if(myHead.prev==null){
        // Node n = new Node(null);
        // n.next = myHead;
        // myHead.prev = n;
        // return null;}
        
        // If the head is null, return null
        if (myHead == null || myHead.prev == null){
            return null;
        }
        
        myHead = myHead.prev;
        
        return myHead.contents;
    }

    public E getPrev () {
        return myHead.prev.contents;
    }

    public E getNext () {
        return myHead.next.contents;
    }

    /**
     * Add Node to the end of the list
     */
    public void add (E input) {
        // if no state has been added yet, create a new one
        // and allow for head and start to point to it
        Node n = new Node(input);
        if (myHead == null) {
            myHead = n;
            myStart = n;
        }
        // otherwise add the StateNode to the beginning
        // of the list
        else {
            myHead.next = n;
            n.prev = myHead;
            myHead = myHead.next;
        }
    }

    /**
     * Returns list of all Node's contents
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

    public boolean hasNext () {
        if (myHead == null) {
            return false;
        }
        return myHead.next != null;
    }

    public boolean isEmpty () {
        return myHead == null;
    }

    public boolean hasPrev () {
        if (myHead == null) {
            return false;
        }
        
        return (myHead.prev != null);
    }

    private class Node {

        private E contents;
        private Node next;
        private Node prev;

        private Node (E input) {
            contents = input;
        }

        public String toString () {
            return "" + contents;
        }
    }
}
