package org.xine.xebuy.business;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class DaoClient {

    @EJB
    CrudService crudService;

    // public List<Book> find(String name, int numerOfPages) {
    //
    //
    // this.crudService.findByNativeQuery(BooK.BY_NAME_AND_PAGES,
    // WithAnnotations()
    // }

}
