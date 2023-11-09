package model;

import static model.Uteis.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pizzaria {
    private int pizzasServidas = 0;
    private int mediaIngredientes;
    private int quantidadeIngredientes;
    private final Map<String, Integer> ingredients = new HashMap<>();
    private final List<Pizza<String>> pizzas = new ArrayList<>();
    private final List<Pedido> pedidos = new ArrayList<>();


    public void menu(){
        ingredients.put("frango",0);
        ingredients.put("catupiry",0);
        ingredients.put("calabresa",0);
        ingredients.put("pepperoni",0);
        ingredients.put("cheddar",0);
        ingredients.put("bacon",0);
        int escolha = 0;
        System.out.println("Seja bem-vindo(a) ao melhor sistema de pizzaria do universo!");
        while (escolha!=6){
            mostrarLinha();
            escolha = leInt("""
                            1) Criar uma pizza
                            2) Criar um novo pedido
                            3) Servir um pedido
                            4) Adicionar ingredientes
                            5) Estatísticas dos pedidos
                            6) Sair do programa
                            O que iremos fazer agora?\s""");
            mostrarLinha();
            int escolhas;
            switch (escolha) {
                case 1 -> {
                    boolean continua = !ingredients.isEmpty();
                    if (!continua) {
                        System.out.println("Não temos nenhum ingrediente disponível.");
                    }
                    Pizza<String> novaPizza = new Pizza<>();
                    String confirmacao;
                    while (continua && novaPizza.size() < 5) {
                        if (!novaPizza.isEmpty()) {
                            confirmacao = leString("Deseja remover o último item escolhido? ");
                            if (confirmacao.toLowerCase().startsWith("s"))
                                System.out.println(novaPizza.pop() + " foi excluído com sucesso!");
                            Uteis.mostrarLinha();
                        }
                        listaIngredientes();

                        confirmacao = leString("Sua escolha: ").toLowerCase();
                        if(ingredients.get(confirmacao) != null){
                            novaPizza.push(confirmacao);
                            System.out.println(confirmacao + " foi adicionado à pizza com sucesso!");
                        }else{
                            System.out.println("Opção inválida!");
                        }
                        if(novaPizza.size() < 5){
                            confirmacao = leString("Deseja continuar adicionando ingredientes? ");
                            if (!confirmacao.toLowerCase().startsWith("s"))
                                continua = false;
                        }
                    }
                    if (!novaPizza.isEmpty()) {
                        confirmacao = leString("Nomeie sua pizza: ");
                        novaPizza.setNomePizza(confirmacao);
                        criarPizza(novaPizza);
                    }
                    else{
                        System.out.println("Você será redirecionado ao menu.");
                    }
                }

                case 2 -> {
                    if(pizzas.isEmpty()) {
                        System.out.println("Nenhuma pizza foi criada. Não será possível criar um pedido!");
                        break;
                    }
                    escolhas = leInt("Digite o código da pizza: ");
                    if (escolhas > pizzas.size() || escolhas <= 0) {
                        System.out.println("Código inexistente.");
                        break;
                    }
                    escolhas--;
                    Pizza<String> pizza = pizzas.get(escolhas);
                    boolean jaEscolhido = pedidos.stream().anyMatch(p-> p.getPedido().equals(pizza));
                    if(jaEscolhido){
                        System.out.println("Já foi criado um pedido para essa pizza!");
                        break;
                    }
                    escolhas = leInt("Digite o número do planeta que fez o pedido: ");
                    novoPedido(pizza, escolhas);
                }

                case 3 -> {
                    if (pedidos.size() > 0) {
                        servirPedido();
                    } else {
                        System.out.println("Nenhum pedido na fila.");
                    }
                }

                case 4 -> {
                    final String nomeIngrediente = leString("Digite o nome do novo ingrediente: ")
                            .toLowerCase();
                    boolean b = ingredients.containsKey(nomeIngrediente);
                    if (b) {
                        System.out.println("Esse ingrediente já está presente em nossa cozinha.");
                    } else {
                        ingredients.put(nomeIngrediente, 0);
                        System.out.println("O " + nomeIngrediente + " foi adicionado com sucesso!");
                    }
                }

                case 5 -> {
                    if (pizzasServidas > 0) {
                        mostraEstatisticas();
                    } else {
                        System.out.println("Nenhuma pizza foi servida.");
                    }
                }

                case 6 -> System.out.println("Expediente concluído. Se dirija ao seu planeta e descanse.");
                default ->{
                    System.out.println("Opção inválida!");
                    mostrarLinha();
                }
            }
        }
    }


    public void criarPizza(Pizza<String> pizza){
        pizzas.add(pizza);
        atualizaEstatisticas(pizza);
        System.out.println("O código da pizza é: " + (pizzas.size()));
    }


    public void novoPedido(Pizza<String> pizza, int enderecoNumero){
        pedidos.add(new Pedido(pizza, enderecoNumero));
        System.out.println("Pedido criado com sucesso. Aguarde! Nós levaremos até o seu planeta.");
        if(pedidos.size()==1){
            System.out.println("Este é o próximo pedido da fila!");
        }else{
            System.out.printf("Este é o %d° pedido da fila!\n",
                    pedidos.size());
        }
    }



    public void servirPedido(){
        Pizza<String> pizza = pedidos.get(0).getPedido();
        if(pizzas.contains(pizza)){
            System.out.printf("Enviamos '%s' para o planeta %d\n",
                    pizza.getNomePizza(),
                    pedidos.get(0).getNumeroMesa());
            this.pizzasServidas++;
            pizzas.remove(pizza);
        }
        else{
            System.out.println("Essa pizza não foi criada...\nNós iremos cancelar este pedido.");
        }
        pedidos.remove(0);
    }


    public void atualizaEstatisticas(Pizza<String> pizza){
        quantidadeIngredientes += pizza.size();
        this.mediaIngredientes = (this.quantidadeIngredientes) / (this.pizzas.size()+pizzasServidas);
        int quantidadeIngredientes = pizza.size();
        for(int i = 0; i < quantidadeIngredientes; i++){
            String ingredienteEscolhido = pizza.pop();
            if(ingredients.containsKey(ingredienteEscolhido)){
                ingredients.put(ingredienteEscolhido, ingredients.get(ingredienteEscolhido)+1);
            }
        }

    }


    public void mostraEstatisticas(){
        String maisVisado = getMaisEscolhido();
        System.out.println("Pizzas servidas: " + this.getPizzasServidas());
        System.out.println("Quantidade média de ingredientes por pizza: " + mediaIngredientes);
        System.out.printf("O favorito é o(a) %s, foi pedido %d vezes.\n",
                maisVisado, ingredients.get(maisVisado));
        if(!getNaoEscolhidos().isEmpty()) {
            System.out.println("Ingredientes que ainda não foram pedidos: " + getNaoEscolhidos());
        }else {
            System.out.println("Todos os ingredientes foram escolhidos pelo menos uma vez.");
        }
    }


    public String getMaisEscolhido() {
        if(ingredients.isEmpty())
            return "Nenhum ingrediente na cozinha";
        Integer maior = 0;
        String nomeDoMaior = "";
        for (String s:ingredients.keySet()) {
            if(ingredients.get(s) > maior){
                maior = ingredients.get(s);
                nomeDoMaior = s;
            }
        }
        return nomeDoMaior;
    }


    public void listaIngredientes(){
        System.out.println("Ingredientes disponíveis: ");
        for (String s : ingredients.keySet()) {
            System.out.printf("%s - %d\n", s, ingredients.get(s));
        }
    }


    public int getPizzasServidas() {
        return pizzasServidas;
    }


    public List<String> getNaoEscolhidos(){
        List<String> zeroVezes = new ArrayList<>();
        for (String s : ingredients.keySet()) {
            if (ingredients.get(s) == 0){
                zeroVezes.add(s);
            }
        }
        return zeroVezes;
    }



    public static void main(String[] args) {
        Pizzaria pi = new Pizzaria();
        pi.menu();
    }
}