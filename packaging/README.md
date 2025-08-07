# Commands required to build the flatpak:

- `flatpak-builder --force-clean --repo=rep build-dir io.github.redddfoxxyy.pomolin.yml`
- `flatpak build-bundle repo pomolin.flatpak io.github.redddfoxxyy.pomolin`

# Commands required to install the built flatpak:

- `flatpak install --user pomolin.flatpak`

# Commands required to remove the installed flatpak:

- `flatpak remove io.github.redddfoxxyy.pomolin`

# Commands required to lint the built flatpak:

- `flatpak run --command=flatpak-builder-lint org.flatpak.Builder repo repo`
- `flatpak run --command=flatpak-builder-lint org.flatpak.Builder manifest io.github.redddfoxxyy.pomolin.yml`

