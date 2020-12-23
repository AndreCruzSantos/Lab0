package com.pa.proj2020.adts.graph;

import java.util.*;

public class DigraphList<V,E> implements Digraph<V,E> {

    private Map<V,Vertex<V>> vertices;
    private Map<E,Edge<E,V>> edges;

    public DigraphList(){
        vertices = new HashMap<>();
        edges = new HashMap<>();
    }


    @Override
    public int numVertices() {
        return vertices.size();
    }

    @Override
    public int numEdges() {
        return edges.size();
    }

    @Override
    public Collection<Vertex<V>> vertices() {
        List<Vertex<V>> list = new ArrayList<>();
        for(Vertex<V> v : vertices.values()){
            list.add(v);
        }
        return list;
    }

    @Override
    public Collection<Edge<E, V>> edges() {
        List<Edge<E,V>> list = new ArrayList<>();
        for(Edge<E,V> e : edges.values()){
            list.add(e);
        }
        return list;
    }

    @Override
    public Collection<Edge<E, V>> incidentEdges(Vertex<V> inbound) throws InvalidVertexException {
        checkVertex(inbound);

        List<Edge<E,V>> incidentEdges = new ArrayList<>();
        for(Edge<E,V> e: edges.values()){
            if(((MyEdge)e).getInbound() == inbound) {
                incidentEdges.add(e);
            }
        }
        return incidentEdges;
    }



    @Override
    public Vertex<V> opposite(Vertex<V> v, Edge<E, V> e) throws InvalidVertexException, InvalidEdgeException {
        checkVertex(v);
        MyEdge edge = checkEdge(e);

        if(!edge.contains(v)){
            return null;
        }

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
        for(Edge<E,V> e: edges.values()){
            if(((MyEdge)e).getOutbound() == outbound) {
                outboundEdges.add(e);
            }
        }
        return outboundEdges;
    }

    @Override
    public boolean areAdjacent(Vertex<V> outbound, Vertex<V> inbound) throws InvalidVertexException {
        checkVertex(outbound);
        checkVertex(inbound);

        for(Edge<E,V> e: edges.values()){
            if(((MyEdge)e).getOutbound() == outbound && ((MyEdge)e).getInbound() == inbound){
                return true;
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

        vertices.put(vElement,newV);

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

        outboundVertex.addEdge(edge);
        edges.put(edgeElement,edge);
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

        outboundVertex.addEdge(edge);
        edges.put(edgeElement,edge);

        return edge;
    }

    @Override
    public V removeVertex(Vertex<V> v) throws InvalidVertexException {

        checkVertex(v);

        V element = v.element();


        Collection<Edge<E,V>> incidentEdges = incidentEdges(v);

        for(Edge<E,V> inEdge : incidentEdges){
            edges.remove(inEdge.element());
        }

        vertices.remove(v);

        return element;
    }

    @Override
    public E removeEdge(Edge<E, V> e) throws InvalidEdgeException {

        MyEdge edge = checkEdge(e);

        for(Vertex<V> v: vertices.values()){
            MyVertex vertex = checkVertex(v);
            if(vertex.getList().contains(e)){
                vertex.getList().remove(e);
            }
        }

        edges.remove(e);

        return edge.element;
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
        for(Vertex<V> v: vertices.values()){
            if(v.element().equals(element)){
                return (MyVertex)v;
            }
        }
        return null;
    }

    private boolean existsVertex(V element){
        return vertices.containsKey(element);
    }

    private boolean existsEdge(E element){
        return edges.containsKey(element);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("Graph with " + numVertices() + "vertices and " + numEdges() +" edges:\n");

        sb.append("--- Vertices: \n");

        for(Vertex<V> v : vertices.values()){
            sb.append("\t").append(v.toString()).append("\n");
        }

        sb.append("\n--- Edges: \n");

        for (Edge<E, V> e : edges.values()) {
            sb.append("\t").append(e.toString()).append("\n");
        }

        return sb.toString();
    }

    private class MyVertex implements Vertex<V> {

        V element;
        List<Edge<E,V>> list;

        public MyVertex(V element){
            this.element = element;
            this.list = new ArrayList<>();
        }

        @Override
        public V element() {
            return this.element;
        }

        public void addEdge(Edge<E,V> e){
            if(e!= null) {
                list.add(e);
            }
        }

        public List<Edge<E,V>> getList(){
            return list;
        }

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

        if(!vertices.containsKey(vertex.element)){
            throw new InvalidVertexException("Vertex does not belong to this graph.");
        }

        return vertex;
    }

    private MyEdge checkEdge(Edge<E,V> e) throws InvalidEdgeException{
        if(e == null) throw new InvalidEdgeException("Null Edge!");

        MyEdge edge;

        try{
            edge = (MyEdge) e;
        } catch(ClassCastException ex){
            throw new InvalidEdgeException("Not an edge.");
        }


        if(!edges.containsKey(edge.element)){
            throw new InvalidEdgeException("Edge does not belong to this graph." + ((MyEdge) e).element);
        }

        return edge;
    }
}
