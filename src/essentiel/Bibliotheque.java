package essentiel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import essentiel.doc.Document;

public class Bibliotheque {
    private List<Document> documents;
    private Map<Integer, Document> documentMap;

    public Bibliotheque() {
        this.documents = new ArrayList<>();
        this.documentMap = new HashMap<>();
    }

    public void ajouterDocument(Document document) {
        documents.add(document);
        documentMap.put(document.getId(), document);
    }

    public Document trouverDocumentParId(int id) {
        return documentMap.get(id);
    }

    public void afficherDocuments() {
        for (Document doc : documents) {
            doc.afficherDetails();
        }
    }
}
