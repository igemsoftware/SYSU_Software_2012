package org.KeggNetwork;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.dom4j.Element;

public class Reaction {

    public class Substrate {

        public String id = "not init";
        public String name = "not init";

        public Substrate(Element element) {
            id = element.attributeValue("id");
            name = element.attributeValue("name");
        }

        public void PrintAll() {
            System.out.print(this.id + ' ');
            System.out.print(this.name + ' ');
            System.out.println();
        }
    }

    public class Product {

        public String id = "not init";
        public String name = "not init";

        public Product(Element element) {
            id = element.attributeValue("id");
            name = element.attributeValue("name");
        }

        public void PrintAll() {
            System.out.print(this.id + ' ');
            System.out.print(this.name + ' ');
            System.out.println();
        }
    }
    public String id = "not init";
    public String name = "not init";
    public String type = "not init";
    public List<Substrate> mSubstrate = new ArrayList<Substrate>();
    public List<Product> mProduct = new ArrayList<Product>();

    public Reaction(final Element element) {
        id = element.attributeValue("id");
        name = element.attributeValue("name");
        type = element.attributeValue("type");
        List<Element> substrates = element.elements("substrate");
        if (substrates != null) //Traversing the substrate node
        {
            for (Iterator<Element> it = substrates.iterator(); it.hasNext();) {
                Element elm = (Element) it.next();
                Substrate ent = new Substrate(elm);
                this.mSubstrate.add(ent);
            }
        } else {
            System.out.println("null substrate at" + this.id);
        }

        List<Element> products = element.elements("product");
        if (products != null) //Traversing the product node
        {
            for (Iterator<Element> it = products.iterator(); it.hasNext();) {
                Element elm = (Element) it.next();
                Product ent = new Product(elm);
                this.mProduct.add(ent);
            }
        } else {
            System.out.println("null product at" + this.id);
        }
    }

    public void PrintAll() {
        System.out.print(this.id + ' ');
        System.out.print(this.name + ' ');
        System.out.print(this.type + ' ');
        System.out.println();
        for (Iterator<Substrate> it = this.mSubstrate.iterator(); it.hasNext();) {
            Substrate elm = (Substrate) it.next();
            elm.PrintAll();
        }
        for (Iterator<Product> it = this.mProduct.iterator(); it.hasNext();) {
            Product elm = (Product) it.next();
            elm.PrintAll();
        }

    }
}
