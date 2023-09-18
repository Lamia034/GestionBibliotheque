package interfaces;

import dto.Client;





public interface ClientInterface {
    Integer add(Client client);
    Client searchByNum(Integer searchNum);

    Client update(Client clientToUpdate);
    boolean deleteByN(Integer deleteN);

}

