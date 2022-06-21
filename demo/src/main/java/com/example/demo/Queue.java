package com.example.demo;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class Queue<E> {
    private java.util.LinkedList<E> list = new LinkedList<>();

    public Queue(E[] e) {
        for (E value : e) {
            enqueue(value);
        }
    }

    public Queue(){

    }

    public void enqueue(E e){
        list.addLast(e);
    }

    public E dequeue(){
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        else{
            return list.removeFirst();
        }
    }

    public E getElement(int i){
        if(i < 0 || i >= getSize()){
            throw new IndexOutOfBoundsException();
        }
        else{
            return list.get(i);
        }
    }

    public E peek(){
        if(isEmpty()){
            return null;
        }
        else{
            return list.get(0);
        }
    }

    public int getSize(){
        return list.size();
    }

    public boolean contains(E e){
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        else{
            return list.contains(e);
        }
    }

    public boolean isEmpty(){
        return list.isEmpty();
    }

    public String toString(){
        return list.toString();
    }

}
