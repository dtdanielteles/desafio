package br.com.bb.t99.persistence.dao;

import br.com.bb.t99.exception.ErroSistema;
import br.com.bb.t99.persistence.models.Pagamento;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class PagamentoDao {

    EntityManager em;

    public PagamentoDao(EntityManager em){
        this.em = em;
    }

    public List<Pagamento> buscarPagamentos() {
        String nameQuery = "CONSULTAR_PAGAMENTO";

        TypedQuery<Pagamento> query = em
                .createNamedQuery(nameQuery, Pagamento.class);

        try {
            return query.getResultList();
        } catch (NoResultException e){
            return new ArrayList<>();
        } catch (PersistenceException e) {
            throw new RuntimeException(e);
        }
    }

    public Pagamento buscarPagamento(Long id) {
        String nameQuery = "CONSULTAR_PAGAMENTO_ID";

        TypedQuery<Pagamento> query = em
                .createNamedQuery(nameQuery, Pagamento.class);

        query.setParameter("idPagamento", id);

        try {
            return query.getSingleResult();
        } catch (NoResultException e){
            return null;
        } catch (PersistenceException e){
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public int publicarPagamento(Pagamento pagamento) {
        String nameQuery = "PUBLICAR-PAGAMENTO";
        return insertOrUpdate(pagamento, nameQuery);
    }

    private int insertOrUpdate(Pagamento pagamento, String nameQuery){
        Query query = em
                .createNamedQuery(nameQuery);

        query.setParameter("idPagamento", pagamento.getId());
        //query.setParameter("")
    }
}
