deps:
	npm ci

repl:
	npx shadow-cljs browser-repl

start:
	npx shadow-cljs watch app

build:
	npx shadow-cljs release app
