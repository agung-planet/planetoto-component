# Planetoto Component
[![](https://jitpack.io/v/oddy-dev/planetoto-component.svg)](https://jitpack.io/#oddy-dev/planetoto-component)

Planetoto Component is a collection of custom components used in the Planetoto project. Choose according the project used to be. 

## How to install
### Jitpack
Type this on your build.gradle (project)
```sh
allprojects {
    repositories {
      ...
        maven { url "https://jitpack.io" }
    }
}
```

Because this project contains several module components, you have to implement modules according what project you used.
Pattern : 
```sh
implementation "com.github.oddy-dev.planetoto-component:ModuleName:master-SNAPSHOT"
```
For example:
```sh
implementation "com.github.oddy-dev.planetoto-component:customer-component:master-SNAPSHOT"
```

## License
    Copyright (c) 2022 oddy-dev
    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
    MERCHANTABILITY,FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
    FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION 
    WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.