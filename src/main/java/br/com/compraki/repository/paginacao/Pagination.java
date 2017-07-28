package br.com.compraki.repository.paginacao;

import java.util.Optional;

import org.springframework.data.domain.Sort;

public class Pagination {

	private static final int INITIAL_PAGE = 0;

	private static final int INITIAL_PAGE_SIZE = 5;

	public static final int[] PAGE_SIZES = { 5, 10, 15 };

	public int getEvalPageSize(Optional<Integer> pageSize) {
		return pageSize.orElse(INITIAL_PAGE_SIZE);
	}

	public int getEvalPage(Optional<Integer> page) {
		return (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
	}

	public Sort getSort(String sort) {
		if (sort != null) {
			String propriedades[] = sort.split(",");
			return new Sort((propriedades[1].equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC),
					propriedades[0]);
		}
		return null;
	}

}