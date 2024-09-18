# graph-centrality

A Clojure app that implements Graph Closest Centrality and exposes an API to handle the graph edges.

## Usage

Install the dependencies

    lein install

Run the app or the ring server to interact with the api

### Server

Start the ring server

    lein ring server-headless

Compute Current Centrality

    curl -i http://localhost:3000/centrality

Post new Edge (will save to `resources/edges`)

    curl -XPOST --header 'Content-Type: text/plain' \
        -d "63 101" \
        -i http://localhost:3000/add-edge

### Repl

Start the repl

    lein repl

Run the centrality computation

    (-main)

Interact with the graph and its contents (edges are in `resources/edges`)

    (let [g (graph-load filepath)]
        (println g)
        (println (graph-closeness g))
        (graph-centrality g))

## License

GPLv2