# clojurize-data

A tiny library which converts strings in maps to keywords and optionally replaces underscores with hyphens.
I found myself writing this kind of function often when dealing with json input, where you might want to have
a more clojury looking map further down the process.

## Usage

```
(use 'clojurize-data.core :refer [clojurize])
```

```
(clojurize {"a" "b"}) -> {:a "b"}
```

## License

Copyright © 2014 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
