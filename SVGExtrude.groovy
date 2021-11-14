import eu.mihosoft.vrl.v3d.svg.*;

import com.neuronrobotics.bowlerstudio.scripting.ScriptingEngine

import eu.mihosoft.vrl.v3d.CSG
import eu.mihosoft.vrl.v3d.Extrude;
import eu.mihosoft.vrl.v3d.Polygon

File f = ScriptingEngine
	.fileFromGit(
		"https://github.com/madhephaestus/SVGBowlerExtrude.git",//git repo URL
		"master",//branch
		"technocopia-pin.svg"// File from within the Git repo
	)
println "Extruding SVG "+f.getAbsolutePath()
SVGLoad s = new SVGLoad(f.toURI())
println "Layers= "+s.getLayers()
// A map of layers to polygons
HashMap<String,List<Polygon>> polygonsByLayer = s.toPolygons()
// extrude all layers to a map to 10mm thick
//HashMap<String,ArrayList<CSG>> csgByLayers = s.extrudeLayers(10)
// extrude just one layer to 10mm
def base = s.extrudeLayerToCSG(3,"base")
// seperate holes and outsides using layers to differentiate
def cutout = s.extrudeLayerToCSG(10,"inner")
					.difference(base)

return [CSG.unionAll([boarderParts,outsideParts]),s.extrudeLayerToCSG(2,"4-star")]