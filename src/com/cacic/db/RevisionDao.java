package com.cacic.db;

import java.sql.Date;
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
	List<Trabajo> getTrabajosByEvaluadorAndDateRange(Integer id, Date desde, Date hasta);
	List<Trabajo> getTrabajosByAutor(Integer id);
	List<Trabajo> getTrabajosByAutorRevisorArea(Integer autorId, Integer revisorId, String area);
}
