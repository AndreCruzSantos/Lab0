package com.pa.proj2020.adts.graph;

import java.util.*;

public class DigraphList<V,E> implements Digraph<V,E> {

    private Map<Vertex<V>, List<Edge<E,V>>> listAdj;

    public DigraphList(){
        listAdj = new HashMap<>();
    }


    @Override
    public int numVertices() {
        return listAdj.keySet().size();
    }

    @Override
    public int numEdges() {

        int count = 0;
        for (List<Edge<E,V>> edges : listAdj.values()) {
            count = count + edges.size();
        }

        return count;
    }

    @Override
    public Collection<Vertex<V>> vertices() {

        List<Vertex<V>> list = new ArrayList<>();
        for (Vertex<V> v : listAdj.keySet()) {
            list.add(v);
        }
        return list;
    }

    @Override
    public Collection<Edge<E, V>> edges() {

        List<Edge<E,V>> list = new ArrayList<>();
        for(List<Edge<E,V>> edges : listAdj.values()){
            for(Edge<E,V> e : edges){
                list.add(e);
            }
        }

        return list;
    }

    @Override
    public Collection<Edge<E, V>> incidentEdges(Vertex<V> inbound) throws InvalidVertexException {

        checkVertex(inbound);

        List<Edge<E,V>> incidentEdges = new ArrayList<>();
        for(List<Edge<E,V>> edges : listAdj.values()){
            for(Edge<E,V> e: edges){
                if(((MyEdge)e).getInbound() == inbound) {
                    incidentEdges.add(e);
                }
            }
        }
        return incidentEdges;
    }



    @Override
    public Vertex<V> opposite(Vertex<V> v, Edge<E, V> e) throws InvalidVertexException, InvalidEdgeException {
        checkVertex(v);
        MyEdge edge = checkEdge(e);

        if(edge.vertices()[0] == v){
            return edge.vertices()[1];
        }
        else {
            return edge.vertices()[0];
        }
    }

    @Override
    public Collection<Edge<E, V>> outboundEdges(Vertex<V> outbound) throws InvalidVertexException {

        checkVertex(outbound);

        List<Edge<E,V>> outboundEdges = new ArrayList<>();
        for(List<Edge<E,V>> edges : listAdj.values()){
            for(Edge<E,V> e: edges){
                if(((MyEdge)e).getOutbound() == outbound) {
                    outboundEdges.add(e);
                }
            }
        }
        return outboundEdges;
    }

    @Override
    public boolean areAdjacent(Vertex<V> outbound, Vertex<V> inbound) throws InvalidVertexException {

        checkVertex(outbound);
        checkVertex(inbound);

        for(List<Edge<E,V>> list : listAdj.values() ){
            for(Edge<E,V> e: list){
                if(((MyEdge)e).getOutbound() == outbound && ((MyEdge)e).getInbound() == inbound){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Vertex<V> insertVertex(V vElement) throws InvalidVertexException {

        if(existsVertex(vElement)){
            throw new InvalidVertexException("Already exists a vertex with this element.");
        }

        MyVertex newV = new MyVertex(vElement);

        listAdj.put(newV, new ArrayList<>());

        return newV;
    }

    @Override
    public Edge<E, V> insertEdge(Vertex<V> outbound, Vertex<V> inbound, E edgeElement) throws InvalidVertexException, InvalidEdgeException {

        if(existsEdge(edgeElement)){
            throw new InvalidEdgeException("Already exists an edge with this element.");
        }

        MyVertex outboundVertex = checkVertex(outbound);
        MyVertex inboundVertex = checkVertex(inbound);

        MyEdge edge = new MyEdge(edgeElement, outboundVertex, inboundVertex);

        listAdj.get(outboundVertex).add(edge);
        return edge;
    }

    @Override
    public Edge<E, V> insertEdge(V outboundElement, V inboundElement, E edgeElement) throws InvalidVertexException, InvalidEdgeException {

        if(existsEdge(edgeElement)){
            throw new InvalidEdgeException("Already exists an edge with this element.");
        }
        if(!existsVertex(outboundElement)){
            throw new InvalidVertexException("No vertex contains: " + outboundElement);
        }
        if(!existsVertex(inboundElement)){
            throw new InvalidVertexException("No vertex contains: " + inboundElement);
        }

        MyVertex outboundVertex = vertexOf(outboundElement);
        MyVertex inboundVertex = vertexOf(inboundElement);

        MyEdge edge = new MyEdge(edgeElement,outboundVertex,inboundVertex);

        listAdj.get(outboundVertex).add(edge);

        return edge;
    }

    @Override
    public V removeVertex(Vertex<V> v) throws InvalidVertexException {

        checkVertex(v);

        V element = v.element();


        Collection<Edge<E,V>> incidentEdges = incidentEdges(v);
        Collection<Edge<E,V>> outboundEdges = outboundEdges(v);

        for(Edge<E,V> inEdge : incidentEdges){
            listAdj.get(inEdge.vertices()[0]).remove(inEdge);
        }

        listAdj.remove(v);

        return element;

    }

    @Override
    public E removeEdge(Edge<E, V> e) throws InvalidEdgeException {

        checkEdge(e);

        E element = e.element();

        for(Vertex<V> v: listAdj.keySet()){

            if(listAdj.get(v).contains(e)){
                listAdj.get(v).remove(e);
            }
        }

        return element;
    }

    @Override
    public V replace(Vertex<V> v, V newElement) throws InvalidVertexException {
        if(existsVertex(newElement)){
            throw new InvalidVertexException("Already exists a vertex with this element.");
        }

        MyVertex vertex = checkVertex(v);

        V oldVertexElement = vertex.element;
        vertex.element = newElement;

        return oldVertexElement;
    }

    @Override
    public E replace(Edge<E, V> e, E newElement) throws InvalidEdgeException {
        if(existsEdge(newElement)){
            throw new InvalidEdgeException("Already exists an edge with this element.");
        }

        MyEdge edge = checkEdge(e);

        E oldEdgeElement = edge.element;
        edge.element = newElement;

        return oldEdgeElement;
    }

    private MyVertex vertexOf(V element){
        for(Vertex<V> v: listAdj.keySet()){
            if(v.element().equals(element)){
                return (MyVertex)v;
            }
        }
        return null;
    }

    private boolean existsVertex(V element){
        MyVertex v =  vertexOf(element);
        return listAdj.containsKey(v);
    }

    private boolean existsEdge(E element){

        for(Edge<E,V> e: edges()){
            if(e.element() == element){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("Graph with " + numVertices() + "vertices and " + numEdges() +" edges:\n");

        sb.append("--- Vertices: \n");

        for(Vertex<V> v : listAdj.keySet()){
            sb.append("\t").append(v.toString()).append("\n");
        }

        sb.append("\n--- Edges: \n");

        for(List<Edge<E,V>> list: listAdj.values()) {
            for (Edge<E, V> e : list) {
                sb.append("\t").append(e.toString()).append("\n");
            }
        }
        return sb.toString();
    }

    private class MyVertex implements Vertex<V> {

        V element;

        public MyVertex(V element){this.element = element;}

        @Override
        public V element() {return this.element;}

        @Override
        public String toString() {return "Vertex{" + element + "}";}
    }

    private class MyEdge implements Edge<E,V> {
        E element;
        Vertex<V> inboundVertex;
        Vertex<V> outboundVertex;

        public MyEdge(E element, Vertex<V> outboundVertex, Vertex<V> inboundVertex){
            this.element = element;
            this.outboundVertex = outboundVertex;
            this.inboundVertex = inboundVertex;
        }

        @Override
        public E element(){return this.element;}

        public boolean contains(Vertex<V> vertex){return (outboundVertex == vertex || inboundVertex == vertex);}

        @Override
        public Vertex<V>[] vertices(){
            Vertex[] vertices = new Vertex[2];
            vertices[0] = outboundVertex;
            vertices[1] = inboundVertex;

            return vertices;
        }

        @Override
        public String toString(){
            return "Edge{{" + element + "}, vertexOutbound=" + outboundVertex.toString()
                    + ", vertexInbound=" + inboundVertex.toString() + '}';
        }

        public Vertex<V> getOutbound(){return outboundVertex;}

        public Vertex<V> getInbound() {return inboundVertex;}

    }

    private MyVertex checkVertex(Vertex<V> v) throws InvalidVertexException{
        if(v == null) throw new InvalidVertexException("Null Vertex!");

        MyVertex vertex;

        try{
            vertex = (MyVertex) v;
        } catch (ClassCastException e){
            throw new InvalidVertexException("Not a vertex.");
        }

        if(!listAdj.containsKey(v)){
            throw new InvalidVertexException("Vertex does not belong to this graph.");
        }

        return vertex;
    }

    public MyEdge checkEdge(Edge<E,V> e) throws InvalidEdgeException{
        if(e == null) throw new InvalidEdgeException("Null Edge!");

        MyEdge edge;

        try{
            edge = (MyEdge) e;
        } catch(ClassCastException ex){
            throw new InvalidEdgeException("Not an edge.");
        }


        if(!existsEdge(((MyEdge)e).element)){
            throw new InvalidEdgeException("Edge does not belong to this graph." + ((MyEdge) e).element);
        }
        return edge;
    }
}
