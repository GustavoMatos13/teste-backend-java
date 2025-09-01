package com.omni.cadastro_cnpj.exception;

public class CnpjNotFoundException extends RuntimeException  {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6735833179923649312L;

	public CnpjNotFoundException(Long id) {
        super("CNPJ com id " + id + " não encontrado.");
    }
}
