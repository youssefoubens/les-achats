package net.youssef.gestion_achats.services;

import net.youssef.gestion_achats.entity.Article;
import net.youssef.gestion_achats.entity.BORDEREAU;
import net.youssef.gestion_achats.repository.Articlerepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    @Autowired
    private Articlerepository articleRepository;

    @Transactional(readOnly = true)
    public List<Article> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        articles.forEach(article -> Hibernate.initialize(article.getTypes()));
        return articles;
    }

    @Transactional(readOnly = true)
    public Article getArticleById(Long id) {
        Optional<Article> articleOptional = articleRepository.findById(id);
        if (articleOptional.isPresent()) {
            Article article = articleOptional.get();
            Hibernate.initialize(article.getTypes());
            return article;
        }
        return null;
    }

    @Transactional
    public Article saveArticle(Article article) {
        return articleRepository.save(article);
    }

    @Transactional
    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Article findByName(String name) {
        Article article = articleRepository.findByName(name);
        if (article != null) {
            Hibernate.initialize(article.getTypes());
        }
        return article;
    }

    public void saveAllArticles(List<Article> articles) {
        articleRepository.saveAll(articles);
    }

    public List<Article> getArticlesByBordereau(BORDEREAU bordereau) {
        // Implement the method logic here
        return articleRepository.findByBordereau(bordereau);
    }

    public Article findByN(String nBrdVe) {
        // Assuming the Article entity has a field 'n' that corresponds to the "N" field
        return (Article) articleRepository.findByN(nBrdVe).orElse(null);
    }
}
