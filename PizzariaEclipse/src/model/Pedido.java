package model;

public class Pedido {
    private Pizza<String> pedido;
    private int numeroMesa;
    public Pedido(Pizza<String> pizza, int mesaCliente){
        this.setPedido(pizza);
        this.setNumeroMesa(mesaCliente);
    }

    public Pizza<String> getPedido() {
        return pedido;
    }

    public void setPedido(Pizza<String> pedido) {
        this.pedido = pedido;
    }

    public int getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(int numeroMesa) {
        this.numeroMesa = numeroMesa;
    }
}
