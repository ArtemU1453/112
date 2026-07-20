import { useEffect, useRef } from 'react';
import Map from 'ol/Map';
import View from 'ol/View';
import TileLayer from 'ol/layer/Tile';
import OSM from 'ol/source/OSM';
import VectorLayer from 'ol/layer/Vector';
import VectorSource from 'ol/source/Vector';
import Feature from 'ol/Feature';
import Point from 'ol/geom/Point';
import { fromLonLat } from 'ol/proj';
import { Circle as CircleStyle, Fill, Stroke, Style, Text } from 'ol/style';
import 'ol/ol.css';
import { useAppSelector } from '@/app/hooks';
import type { Incident, Unit } from '@/types/domain';

const MINSK_CENTER = fromLonLat([27.5615, 53.9023]);

const PRIORITY_COLOR: Record<string, string> = {
  CRITICAL: '#d32f2f',
  HIGH: '#f57c00',
  MEDIUM: '#1976d2',
  LOW: '#757575',
};

const UNIT_COLOR: Record<string, string> = {
  AVAILABLE: '#2e7d32',
  DISPATCHED: '#f57c00',
  EN_ROUTE: '#f9a825',
  ON_SCENE: '#d32f2f',
  RETURNING: '#1976d2',
  OUT_OF_SERVICE: '#616161',
};

function incidentStyle(incident: Incident): Style {
  return new Style({
    image: new CircleStyle({
      radius: 9,
      fill: new Fill({ color: PRIORITY_COLOR[incident.priority] ?? '#1976d2' }),
      stroke: new Stroke({ color: '#ffffff', width: 2 }),
    }),
    text: new Text({
      text: incident.number,
      offsetY: -16,
      fill: new Fill({ color: '#ffffff' }),
      font: '11px Roboto',
    }),
  });
}

function unitStyle(unit: Unit): Style {
  return new Style({
    image: new CircleStyle({
      radius: 6,
      fill: new Fill({ color: UNIT_COLOR[unit.status] ?? '#616161' }),
      stroke: new Stroke({ color: '#000000', width: 1 }),
    }),
    text: new Text({
      text: unit.callSign,
      offsetY: 14,
      fill: new Fill({ color: '#90caf9' }),
      font: '10px Roboto',
    }),
  });
}

export default function IncidentMap() {
  const mapRef = useRef<HTMLDivElement>(null);
  const mapObj = useRef<Map | null>(null);
  const incidentSource = useRef(new VectorSource());
  const unitSource = useRef(new VectorSource());
  const incidents = useAppSelector((s) => s.incidents.active);
  const units = useAppSelector((s) => s.units.units);

  useEffect(() => {
    if (!mapRef.current || mapObj.current) return;
    mapObj.current = new Map({
      target: mapRef.current,
      layers: [
        new TileLayer({ source: new OSM() }),
        new VectorLayer({ source: unitSource.current }),
        new VectorLayer({ source: incidentSource.current }),
      ],
      view: new View({ center: MINSK_CENTER, zoom: 12 }),
    });
    return () => {
      mapObj.current?.setTarget(undefined);
      mapObj.current = null;
    };
  }, []);

  useEffect(() => {
    incidentSource.current.clear();
    incidents
      .filter((i) => i.latitude && i.longitude)
      .forEach((incident) => {
        const feature = new Feature({
          geometry: new Point(fromLonLat([incident.longitude!, incident.latitude!])),
        });
        feature.setStyle(incidentStyle(incident));
        incidentSource.current.addFeature(feature);
      });
  }, [incidents]);

  useEffect(() => {
    unitSource.current.clear();
    units
      .filter((u) => u.currentLatitude && u.currentLongitude)
      .forEach((unit) => {
        const feature = new Feature({
          geometry: new Point(fromLonLat([unit.currentLongitude!, unit.currentLatitude!])),
        });
        feature.setStyle(unitStyle(unit));
        unitSource.current.addFeature(feature);
      });
  }, [units]);

  return <div ref={mapRef} style={{ width: '100%', height: '100%', minHeight: 400 }} />;
}
