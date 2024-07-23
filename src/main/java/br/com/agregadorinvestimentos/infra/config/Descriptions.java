package br.com.agregadorinvestimentos.infra.config;

public class Descriptions {
    public static final String LIST_USER_DESC = "Método que retorna os dados de usuário caso ele exista , caso contrário retorna um 404 NOT FOUND.";
    public static final String LIST_USERS_DESC = "Método que retorna uma lista de usuários";
    public static final String LIST_ACCOUNT_USER = "Método que lista todas as contas de um usuário, Informe o userId Abaixo:";
    public static final String CREATE_ACCOUNT_USER = "Endpoint para criar uma nova conta de usuário. Se o userId fornecido for válido, a API permite a criação da conta. Caso contrário, retorna um status 404 NOT FOUND.";
    public static final String PUT_USER =  "Método para deletar um usuário pelo userId. Se o userId for válido, a API permite a a conta ser deletada. Caso contrário, retorna um status 404 NOT FOUND.";
    public static final String LIST_STOCKS = "Endpoint que retorna uma lista de ações com preços atualizados em tempo real.";
    public static final String LIST_STOCK_ACCOUNT = "Lista todas as ações associadas a uma conta , mostra a quantidade de ações e o valor total de cada ação";
}
