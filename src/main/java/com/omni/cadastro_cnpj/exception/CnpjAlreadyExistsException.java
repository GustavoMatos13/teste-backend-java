package com.omni.cadastro_cnpj.exception;

public class CnpjAlreadyExistsException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3589511819960507475L;

	public CnpjAlreadyExistsException(String cnpj) {
        super("CNPJ '" + cnpj + "' já existe no sistema.");
    }
}
