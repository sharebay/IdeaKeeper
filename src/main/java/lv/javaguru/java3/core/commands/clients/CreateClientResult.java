package lv.javaguru.java3.core.commands.clients;

import lv.javaguru.java3.core.commands.DomainCommandResult;
import lv.javaguru.java3.core.dto.ClientDTO;

@Deprecated
public class CreateClientResult implements DomainCommandResult {

	private ClientDTO client;

	public CreateClientResult(ClientDTO client) {
		this.client = client;
	}

	public ClientDTO getClient() {
		return client;
	}

}
