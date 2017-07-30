package br.com.compraki.repository.paginacao;

import java.net.URISyntaxException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

public class Pager<T> {

	private Page<T> page;

	private URIBuilder builderPagina;

	private URIBuilder builderSort;

	private int buttonsToShow;

	private int startPage;

	private int endPage;

	public Pager(Page<T> page, HttpServletRequest httpServletRequest) {
		this.page = page;
		String httpUrl = getFullRequestUrl(httpServletRequest);
		try {
			this.builderPagina = new URIBuilder(httpUrl);
			this.builderSort = new URIBuilder(httpUrl);
		} catch (URISyntaxException e) {
			throw new RuntimeException("Url inválida", e);
		}

		setButtonsToShow(getConteudo().size());

		int halfPagesToShow = getButtonsToShow() / 2;

		if (page.getTotalPages() <= getButtonsToShow()) {
			setStartPage(1);
			setEndPage(page.getTotalPages());

		} else if (page.getNumber() - halfPagesToShow <= 0) {
			setStartPage(1);
			setEndPage(getButtonsToShow());

		} else if (page.getNumber() + halfPagesToShow == page.getTotalPages()) {
			setStartPage(page.getNumber() - halfPagesToShow);
			setEndPage(page.getTotalPages());

		} else if (page.getNumber() + halfPagesToShow > page.getTotalPages()) {
			setStartPage(page.getTotalPages() - getButtonsToShow() + 1);
			setEndPage(page.getTotalPages());

		} else {
			setStartPage(page.getNumber() - halfPagesToShow);
			setEndPage(page.getNumber() + halfPagesToShow);
		}

	}

	public boolean isVazia() {
		return page.getContent().isEmpty();
	}

	public List<T> getConteudo() {
		return page.getContent();
	}

	public int getSize() {
		return page.getContent().size();
	}

	public int getButtonsToShow() {
		return buttonsToShow;
	}

	public void setButtonsToShow(int buttonsToShow) {
		this.buttonsToShow = buttonsToShow;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	private String getFullRequestUrl(HttpServletRequest httpServletRequest) {
		return httpServletRequest.getRequestURL()
				.append(httpServletRequest.getQueryString() != null ? "?" + httpServletRequest.getQueryString() : "")
				.toString();
	}

	public String urlParaPagina(String link, int tableSize, int paginaAtual) {
		builderPagina.setParameter("pageSize", String.valueOf(tableSize)).toString();
		builderPagina.setParameter("page", String.valueOf(paginaAtual)).toString();
		String urlPagina = "";
		String url[] = builderPagina.toString().split("codigo=");
		String parametros[] = null;
		if (url.length > 1) {
			parametros = url[1].split("&pageSize=" + tableSize + "&page=" + paginaAtual);
			urlPagina = link + "?codigo=" + parametros[0];
		}
		return urlPagina;
	}

	public String urlOrdenada(String propriedade) {
		String valorSort = String.format("%s,%s", propriedade, inverterDirecao(propriedade));
		return builderSort.setParameter("sort", valorSort).toString();
	}

	public String inverterDirecao(String propriedade) {
		String direcao = "asc";

		Order order = page.getSort() != null ? page.getSort().getOrderFor(propriedade) : null;
		if (order != null) {
			direcao = Sort.Direction.ASC.equals(order.getDirection()) ? "desc" : "asc";
		}

		return direcao;
	}

	public boolean descendente(String propriedade) {
		return inverterDirecao(propriedade).equals("asc");
	}

	public boolean ordenada(String propriedade) {
		Order order = page.getSort() != null ? page.getSort().getOrderFor(propriedade) : null;

		if (order == null) {
			return false;
		}

		return page.getSort().getOrderFor(propriedade) != null ? true : false;
	}

	@Override
	public String toString() {
		return "Pager [startPage=" + startPage + ", endPage=" + endPage + "]";
	}

}