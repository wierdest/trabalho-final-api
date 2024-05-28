package br.org.serratec.api.cel.service;

import br.org.serratec.api.cel.dtos.ItemRelatorioDTO;
import br.org.serratec.api.cel.dtos.RelatorioPedidoDTO;

public class GeradorRelatorioHtml {
	
	public static String gerarHtmlRelatorio(RelatorioPedidoDTO relatorio) {
        
		StringBuilder html = new StringBuilder();

        // Cabeçalho do HTML
        html.append("<html><head><title>Relatório de Pedido</title></head><body>");

        // Título do relatório
        html.append("<h1>Relatório de Pedido</h1>");

        // Informações do pedido
        html.append("<h2>Pedido #" + relatorio.idPedido() + "</h2>");
        html.append("<p><strong>Data do Pedido:</strong> " + relatorio.dataPedido() + "</p>");
        html.append("<p><strong>Data de Entrega:</strong> " + relatorio.dataEntrega() + "</p>");
        html.append("<p><strong>Valor Total:</strong> R$" + relatorio.valorTotal() + "</p>");

        // Tabela de itens do pedido
        html.append("<h2>Itens do Pedido</h2>");
        html.append("<table border=\"1\"><tr>");
        html.append("<th>Código</th><th>Nome do Produto</th><th>Preço de Venda</th><th>Quantidade</th>");
        html.append("<th>Valor Bruto</th><th>Percentual Desconto</th><th>Valor Líquido</th></tr>");
        
        for (ItemRelatorioDTO item : relatorio.itens()) {
            html.append("<tr>");
            html.append("<td>" + item.codigo() + "</td>");
            html.append("<td>" + item.nomeProduto() + "</td>");
            html.append("<td>R$" + item.precoVenda() + "</td>");
            html.append("<td>" + item.quantidade() + "</td>");
            html.append("<td>R$" + item.valorBruto() + "</td>");
            html.append("<td>" + item.percentualDesconto() + "%</td>");
            html.append("<td>R$" + item.valorLiquido() + "</td>");
            html.append("</tr>");
        }
        html.append("</table>");

        // Finalizando o HTML
        html.append("</body></html>");

        return html.toString();
    }

}
