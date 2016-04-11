# graph-centrality

A Clojure app that implements Graph Closest Centrality and exposes an API to handle the graph edges.

## Usage

Run the app or the ring server to interact with the api
    
**Server**

    lein ring server
    # Load Centrality
    curl -i http://localhost:3000/centrality
    # Post new Edge
    curl -XPOST --header 'Content-Type: text/plain' \
        -d "63 101" \
        -i http://localhost:3000/add-edge

**Repl**

    lein repl
    (-main)
    (let g (graph-load filepath)
        (println g)
        (println (graph-closeness g))
        (graph-centrality g))

## License

GPLv2