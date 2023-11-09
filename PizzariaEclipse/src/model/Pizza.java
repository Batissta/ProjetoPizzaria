package model;

public class Pizza<E>{
    private static final int tamanho = 5;
    private String nomePizza;
	@SuppressWarnings("unchecked")
	private E[] pilha = (E[]) new Object[tamanho];

    private int quantidade = 0;

    public void push(E e){
        pilha[quantidade] = e;
        this.quantidade++;
    }
    public E pop(){
        quantidade--;
        E temp = pilha[quantidade];
        pilha[quantidade] = null;
        return temp;
    }

    public E first(){
        return pilha[quantidade-1];
    }

    public boolean isEmpty(){
        return quantidade == 0;
    }

    public int size(){
        return quantidade;
    }

    public String getNomePizza() {
        return nomePizza;
    }

    public void setNomePizza(String nomePizza) {
        this.nomePizza = nomePizza;
    }
}
