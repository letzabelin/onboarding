COMMIT_HASH := $(shell git rev-parse --short=7 HEAD)

deps:
	npm ci

repl:
	npx shadow-cljs browser-repl

watch:
	npx shadow-cljs watch app

release:
	npx shadow-cljs release app

docker:
	docker buildx build . --push --platform linux/amd64,linux/arm64 -t 1ocke/onboarding-app:$(COMMIT_HASH) -t 1ocke/onboarding-app:latest
