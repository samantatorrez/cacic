package com.cacic.db;

import java.util.List;

import com.cacic.entity.Revision;
import com.cacic.entity.Trabajo;

public interface RevisionDao {
	public Integer altaRevision(Revision revision);
	public Revision getRevision(Integer id);
	public List<Revision> getRevisiones();
	public void bajaRevision(Integer id);
	public void eliminarDatos();
	List<Trabajo> getTrabajosByEvaluador(Integer id);
}
