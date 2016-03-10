package ru.skysoftlab.crongen;

import java.util.Collection;

import com.vaadin.data.Property;
import com.vaadin.data.Validatable;
import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.ui.AbstractJavaScriptComponent;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

public abstract class AbstractJavaScriptTextField extends
		AbstractJavaScriptComponent implements Field<String>, Validatable {

	private static final long serialVersionUID = 6954392091393553620L;

	protected TextField delegate;

	public AbstractJavaScriptTextField() {
		delegate = new TextField();
	}

	public AbstractJavaScriptTextField(String caption) {
		delegate = new TextField(caption);
	}

	public AbstractJavaScriptTextField(String caption, String value) {
		delegate = new TextField(caption, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.ui.AbstractComponent#getErrorMessage()
	 */
	@Override
	public com.vaadin.server.ErrorMessage getErrorMessage() {
		return delegate.getErrorMessage();
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.ui.AbstractComponent#focus()
	 */
	@Override
	public void focus() {
		super.focus();

		maybeMarkAsDirty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vaadin.data.Property.ValueChangeNotifier#addValueChangeListener(com
	 * .vaadin.data.Property.ValueChangeListener)
	 */
	@Override
	public void addValueChangeListener(
			com.vaadin.data.Property.ValueChangeListener listener) {
		delegate.addValueChangeListener(listener);

		maybeMarkAsDirty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vaadin.data.Property.ValueChangeNotifier#addListener(com.vaadin.data
	 * .Property.ValueChangeListener)
	 */
	@Override
	public void addListener(
			com.vaadin.data.Property.ValueChangeListener listener) {
		delegate.addListener(listener);

		maybeMarkAsDirty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vaadin.data.Property.ValueChangeNotifier#removeValueChangeListener(
	 * com.vaadin.data.Property.ValueChangeListener)
	 */
	@Override
	public void removeValueChangeListener(
			com.vaadin.data.Property.ValueChangeListener listener) {
		delegate.removeValueChangeListener(listener);

		maybeMarkAsDirty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vaadin.data.Property.ValueChangeNotifier#removeListener(com.vaadin.
	 * data.Property.ValueChangeListener)
	 */
	@Override
	public void removeListener(
			com.vaadin.data.Property.ValueChangeListener listener) {
		delegate.removeListener(listener);

		maybeMarkAsDirty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.data.BufferedValidatable#isInvalidCommitted()
	 */
	@Override
	public boolean isInvalidCommitted() {
		return delegate.isInvalidCommitted();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.data.BufferedValidatable#setInvalidCommitted(boolean)
	 */
	@Override
	public void setInvalidCommitted(boolean isCommitted) {
		delegate.setInvalidCommitted(isCommitted);

		maybeMarkAsDirty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.data.Buffered#commit()
	 */
	@Override
	public void commit() throws SourceException, InvalidValueException {
		delegate.commit();

		maybeMarkAsDirty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.data.Buffered#discard()
	 */
	@Override
	public void discard() throws SourceException {
		delegate.discard();

		maybeMarkAsDirty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.data.Buffered#setBuffered(boolean)
	 */
	@Override
	public void setBuffered(boolean buffered) {
		delegate.setBuffered(buffered);

		maybeMarkAsDirty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.data.Buffered#isBuffered()
	 */
	@Override
	public boolean isBuffered() {
		return delegate.isBuffered();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.data.Buffered#isModified()
	 */
	@Override
	public boolean isModified() {
		return delegate.isModified();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vaadin.data.Property.ValueChangeListener#valueChange(com.vaadin.data
	 * .Property.ValueChangeEvent)
	 */
	@Override
	public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
		delegate.valueChange(event);

		maybeMarkAsDirty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.ui.Component.Focusable#getTabIndex()
	 */
	@Override
	public int getTabIndex() {
		return delegate.getTabIndex();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.ui.Component.Focusable#setTabIndex(int)
	 */
	@Override
	public void setTabIndex(int tabIndex) {
		delegate.setTabIndex(tabIndex);

		maybeMarkAsDirty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.data.Property#getValue()
	 */
	@Override
	public String getValue() {
		return delegate.getValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.data.Property#setValue(java.lang.Object)
	 */
	@Override
	public void setValue(String newValue)
			throws com.vaadin.data.Property.ReadOnlyException {
		delegate.setValue(newValue);

		maybeMarkAsDirty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vaadin.data.Property.Viewer#setPropertyDataSource(com.vaadin.data
	 * .Property )
	 */
	@Override
	public void setPropertyDataSource(Property newDataSource) {
		delegate.setPropertyDataSource(newDataSource);

		maybeMarkAsDirty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.data.Property.Viewer#getPropertyDataSource()
	 */
	@Override
	public Property getPropertyDataSource() {
		return delegate.getPropertyDataSource();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.data.Validatable#addValidator(com.vaadin.data.Validator)
	 */
	@Override
	public void addValidator(Validator validator) {
		delegate.addValidator(validator);

		maybeMarkAsDirty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vaadin.data.Validatable#removeValidator(com.vaadin.data.Validator)
	 */
	@Override
	public void removeValidator(Validator validator) {
		delegate.removeValidator(validator);

		maybeMarkAsDirty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.data.Validatable#removeAllValidators()
	 */
	@Override
	public void removeAllValidators() {
		delegate.removeAllValidators();

		maybeMarkAsDirty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.data.Validatable#getValidators()
	 */
	@Override
	public Collection<Validator> getValidators() {
		return delegate.getValidators();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.data.Validatable#isValid()
	 */
	@Override
	public boolean isValid() {
		return delegate.isValid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.data.Validatable#validate()
	 */
	@Override
	public void validate() throws InvalidValueException {
		delegate.validate();

		maybeMarkAsDirty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.data.Validatable#isInvalidAllowed()
	 */
	@Override
	public boolean isInvalidAllowed() {
		return delegate.isInvalidAllowed();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.data.Validatable#setInvalidAllowed(boolean)
	 */
	@Override
	public void setInvalidAllowed(boolean invalidValueAllowed)
			throws UnsupportedOperationException {
		delegate.setInvalidAllowed(invalidValueAllowed);

		maybeMarkAsDirty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.ui.Field#isRequired()
	 */
	@Override
	public boolean isRequired() {
		return delegate.isRequired();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.ui.Field#setRequired(boolean)
	 */
	@Override
	public void setRequired(boolean required) {
		delegate.setRequired(required);

		maybeMarkAsDirty();
	}

	/**
	 * Checks if the delegate is dirty and if so, marks this component as dirty
	 * as well. This allows the delegate to handle all the logic and
	 * optimizations about dirty status while ensuring that the delegate and
	 * this component stay in sync. This should normally be called in any
	 * mutator.
	 */
	protected void maybeMarkAsDirty() {
		UI ui = getUI();

		if (ui != null && getUI().getConnectorTracker().isDirty(delegate)) {
			markAsDirty();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.ui.Field#setRequiredError(java.lang.String)
	 */
	@Override
	public void setRequiredError(String requiredMessage) {
		delegate.setRequiredError(requiredMessage);

		maybeMarkAsDirty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.ui.Field#getRequiredError()
	 */
	@Override
	public String getRequiredError() {
		return delegate.getRequiredError();
	}

	@Override
	public boolean isEmpty() {
		return delegate.getValue().isEmpty();
	}

	@Override
	public void clear() {
		delegate.setValue("");
	}

	@Override
	public Class<? extends String> getType() {
		return delegate.getType();
	}
}
